package chatbot.api.order.services;

import chatbot.api.common.domain.kakao.openbuilder.responseVer2.QuickReply;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseDtoVerTwo;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseJsonFormat.Component.simpleText.ComponentSimpleText;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseJsonFormat.Component.simpleText.SimpleText;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.SkillTemplate;
import chatbot.api.common.services.KakaoBasicCardResponseService;
import chatbot.api.common.services.KakaoSimpleTextResponseService;
import chatbot.api.mappers.HubMapper;
import chatbot.api.mappers.UserMapper;
import chatbot.api.order.Repository.MainOrderRepositoryImpl;
import chatbot.api.order.domain.DevOrderDto;
import chatbot.api.order.domain.HubOrderDto;
import chatbot.api.order.domain.MainOrder;
import chatbot.api.order.domain.SelectOrderVo;
import chatbot.api.skillHub.domain.HubInfoDto;
import chatbot.api.user.domain.UserInfoDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
@AllArgsConstructor
@Slf4j
public class OrderResponseService {

    private HubMapper hubmapper;

    private UserMapper userMapper;

    private KakaoBasicCardResponseService kakaoBasicCardResponseService;

    private KakaoSimpleTextResponseService kakaoSimpleTextResponseService;

    private MainOrderRepositoryImpl mainOrderRepository;

    private RestTemplate restTemplate;


    // 사용자가 이용할 수 있는 허브들 리턴
    public ResponseDtoVerTwo responserHubs(String providerId) {

        ResponseDtoVerTwo responseDtoVerTwo = new ResponseDtoVerTwo();

        if(providerId == null) {
            return responseDtoVerTwo = kakaoBasicCardResponseService.responserRequestPreSignUp();
        }

        UserInfoDto user = userMapper.getUserByProviderId(providerId).get();
        log.info(user.toString());
        if(user == null) {
            return responseDtoVerTwo = kakaoSimpleTextResponseService.responserShortMsg("등록되지 않은 사용자 입니다.");
        }

        if(!(providerId.equals(user.getProviderId()))) {
            return responseDtoVerTwo = kakaoSimpleTextResponseService.responserShortMsg("아이디가 동일하지 않습니다.");
        }
        
        // 명령 시나리오대로 코드 작성

        // 1. 데이터베이스에서 사용자가 사용할 수 있는 허브목록 Get
        ArrayList<HubInfoDto> hubs = hubmapper.getUserHubsByUserId(user.getUserId());
        log.info(hubs.toString());
        if(hubs == null) {
            return responseDtoVerTwo = kakaoSimpleTextResponseService.responserShortMsg("사용 가능한 허브가 없습니다.");
        }

        // 2. "응답" 꾸미기
        SimpleText text = new SimpleText();
        StringBuffer responseMsg = new StringBuffer("사용할 수 있는 허브 목록 입니다.\n\n");
        responseMsg.append("허브 번호  :  허브 이름 : 허브 설명\n\n");
        for(int i = 0; i < hubs.size(); i++) {
            responseMsg.append((i + 1) + " : " + hubs.get(i).getHubName() + " : " + hubs.get(i).getHubDescript() + "\n");
        }
        responseMsg.append("\n버튼을 클릭해주세요.");
        text.setText(responseMsg.toString());

        ComponentSimpleText simpleText = new ComponentSimpleText();
        simpleText.setSimpleText(text);

        ArrayList<Object> outputs = new ArrayList<Object>();
        outputs.add(simpleText);

        // 3. "버튼" 꾸미기
        ArrayList<QuickReply> quickReplies = new ArrayList<QuickReply>();
        QuickReply[] quickReply = new QuickReply[hubs.size()];
        for (int i = 0; i < hubs.size(); i++) {
            
            QuickReply quick = QuickReply.builder()
                    .label(Integer.toString(i + 1))
                    .action("block")
                    .messageText("허브 " + (i + 1) + "번")
                    .blockId("5c7670efe821274ba78984c4")
                    .build();


            try {
                quickReply[i] = (QuickReply) quick.clone();
                quickReplies.add(quickReply[i]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        SkillTemplate template = new SkillTemplate(outputs, quickReplies);

        responseDtoVerTwo.setVersion("2.0");
        responseDtoVerTwo.setTemplate(template);


        // mainOrder (유지되어야 할 데이터를 담음) In-Memory에 저장
        MainOrder mainOrder = MainOrder.builder()
                .providerId(providerId)
                .build();

        // mainOrder 데이터 set
        HubOrderDto[] hubOrders = new HubOrderDto[hubs.size()];
        for(int i = 0; i < hubs.size(); i++) {
            HubOrderDto hubOrderDto = HubOrderDto.builder()
                    .hubSeq(i + 1)
                    .hubId(hubs.get(i).getHubId())
                    .explicitIp(hubs.get(i).getExternalIp())
                    .explicitPort(hubs.get(i).getExternalPort())
                    .build();
            hubOrders[i] = hubOrderDto;
        }
        mainOrder.setHubOrderList(hubOrders);
        log.info("MainOrders : " + mainOrder.toString());

        // 저장
        mainOrderRepository.save(mainOrder);

        // 5. 사용자에게 응답.
        return responseDtoVerTwo;
    }





    // 사용자가 발화한 허브 번호를 파싱해서 매개변수로 받는다.
    // 사용자 측에 해당 허브가 사용할 수 있는 모듈들 중에서 이전에 발화했던 모듈 타입들을 리스트로 뽑아준다.
    public ResponseDtoVerTwo responserDevsAboutSelectedHub(String providerId, int hubSeq) {

        ResponseDtoVerTwo responseDtoVerTwo = new ResponseDtoVerTwo();

        // redis에서 providerId를 key 값으로해서 데이터를 가져온다.
        MainOrder mainOrder = mainOrderRepository.find(providerId);
        log.info(mainOrder.toString());

        // 전달할 데이터 경로 셋팅
        mainOrder.setSelectOrderVo(SelectOrderVo.builder()
                                .externalIp(mainOrder.getHubOrderList()[(hubSeq - 1)].getExplicitIp())
                                .externalPort(mainOrder.getHubOrderList()[(hubSeq - 1)].getExplicitPort())
                                .build());
        log.info("SelectOrderVo : " + mainOrder.getSelectOrderVo());
        log.info("사용자가 뽑은놈 : " + mainOrder.getHubOrderList()[(hubSeq - 1)].toString());

        // 0. 허브로 restTemplate 를 날려서, 사용가능한 장치들에 대한 데이터를 가져와야 한다.
        String url = new String("http://" + mainOrder.getSelectOrderVo().getExternalIp() + mainOrder.getSelectOrderVo().getExternalPort());
        DevOrderDto[] devs = restTemplate.getForObject(url, DevOrderDto[].class);

        // 0-1. devs를 redis에 저장하는 코드 작성.
       for(int i = 0; i < devs.length; i++) {
           devs[i].setDevSeq(i + 1);
           log.info(i + 1 + "번 : " + devs[i].toString());
       }
        mainOrder.setDevOrderList(devs);
        log.info("Devs : " + devs.toString());
        log.info("MainOrders : " + mainOrder.toString());
        log.info("mainOrder.getDevOrderList() : " + mainOrder.getDevOrderList().toString());

        // 1. 응답 꾸미기
        /*
        devSeq / devName / devIdentifier
         */
        SimpleText text = new SimpleText();
        StringBuffer responseMsg = new StringBuffer("사용할 수 있는 모듈 목록 입니다.");
        responseMsg.append("모듈 번호  :  모듈 이름  :  식별명\n\n");
        for (int i = 0; i < devs.length; i++) {
            responseMsg.append((i + 1) + " : " + devs[i].getDevName() + " : " + devs[i].getDevIdentifier());
        }
        responseMsg.append("\n버튼을 클릭해주세요.");
        text.setText(responseMsg.toString());

        ComponentSimpleText simpleText = new ComponentSimpleText();
        simpleText.setSimpleText(text);

        ArrayList<Object> outputs = new ArrayList<Object>();
        outputs.add(simpleText);



        // 2. 버튼 꾸미기
        ArrayList<QuickReply> quickReplies = new ArrayList<QuickReply>();
        QuickReply[] quickReply = new QuickReply[devs.length];
        for(int i = 0; i < devs.length; i++) {
            QuickReply quick = QuickReply.builder()
                    .label(Integer.toString(i + 1))
                    .action("message")
                    .messageText("모듈" + (i + 1) + "번")
                    .build();

            try {
                quickReply[i] = (QuickReply) quick.clone();
                quickReplies.add(quickReply[i]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        SkillTemplate template = new SkillTemplate(outputs, quickReplies);

        responseDtoVerTwo.setVersion("2.0");
        responseDtoVerTwo.setTemplate(template);



        // 3. redis 로 부터 mainOrder의 값을 가져온다.



        // 4. mainOrder 데이터 set



        // 5. redis에 mainOrder 데이터 저장



        return ResponseDtoVerTwo.builder().build();
    }
}
