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
import chatbot.api.order.domain.HubOrderDto;
import chatbot.api.order.domain.MainOrder;
import chatbot.api.order.domain.SelectOrderVo;
import chatbot.api.skillHub.domain.HubInfoDto;
import chatbot.api.user.domain.UserInfoDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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


    // 사용자가 이용할 수 있는 허브들 중에서 발화한 모듈 타입이 부착된 허브 목록을 responser
    public ResponseDtoVerTwo responserHubsAboutDevType(String providerId, String devCategory) {

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

        // 1. 데이터베이스에서 사용자가 사용할 수 있는 허브들 중에서 사용자 발화와 일치하는 모듈 타입이 부착된 허브 목록을 get
        devCategory = devCategory.toLowerCase();
        ArrayList<HubInfoDto> hubs = hubmapper.getHubInfoByDevCategory(user.getUserId(), devCategory);

        /*
        수정해야 할 부분
        - 동일한 허브가 목록으로 뽑힐 경우 목록에서 제거해줘야 한다.

        - 여기에 코드 작성하면 될듯... 뽑아내자마자 바로 제거해야함...
         */


        log.info(devCategory);
        log.info(hubs.toString());

        if(hubs == null) {
            return responseDtoVerTwo = kakaoSimpleTextResponseService.responserShortMsg("사용할 수 있는 모듈이 부착된 허브가 없습니다.");
        }

        // 2. "응답" 꾸미기
        SimpleText text = new SimpleText();
        StringBuffer responseMsg = new StringBuffer("사용할 수 있는 허브 목록 입니다.\n\n");
        responseMsg.append("허브 번호  :  허브 이름\n\n");
        for(int i = 0; i < hubs.size(); i++) {
            responseMsg.append((i + 1) + " : " + hubs.get(i).getHubName() + "\n");
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
                    .action("message")
                    .messageText("허브 " + (i + 1) + "번")
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
                .devCategory(devCategory)
                .build();

        // mainOrder 데이터 set
        ArrayList<HubOrderDto> hubOrders = new ArrayList<HubOrderDto>();
        for(int i = 0; i < hubs.size(); i++) {
            HubOrderDto hubOrderDto = HubOrderDto.builder()
                    .hubSeq(i + 1)
                    .hubId(hubs.get(i).getHubId())
                    .explicitIp(hubs.get(i).getExternalIp())
                    .explicitPort(hubs.get(i).getExternalPort())
                    .build();
            hubOrders.add(hubOrderDto);
        }
        mainOrder.setHubOrderList(hubOrders);
        log.info(hubOrders.toString());
        log.info(mainOrder.toString());
        log.info(mainOrder.getHubOrderList().toString());

        // 저장
        mainOrderRepository.save(mainOrder);

        // 5. 사용자에게 응답.
        return responseDtoVerTwo;
    }



    // 사용자가 발화한 허브 번호를 파싱해서 매개변수로 받는다.
    // 사용자 측에 해당 허브가 사용할 수 있는 모듈들 중에서 이전에 발화했던 모듈 타입들을 리스트로 뽑아준다.
    public ResponseDtoVerTwo responserDevsAboutSelectedHub(String providerId, int hubSeq) {

        // redis에서 providerId를 key 값으로해서 데이터를 가져온다.
        MainOrder mainOrder = mainOrderRepository.find(providerId);
        log.info(mainOrder.toString());

        // 전달할 데이터 경로 셋팅
        mainOrder.setSelectOrderVo(SelectOrderVo.builder()
                                .externalIp(mainOrder.getHubOrderList().get(hubSeq - 1).getExplicitIp())
                                .externalPort(mainOrder.getHubOrderList().get(hubSeq - 1).getExplicitPort())
                                .build());
        log.info("SelectOrderVo : " + mainOrder.getSelectOrderVo());
        log.info("사용자가 뽑은놈 : " + mainOrder.getHubOrderList().get(hubSeq - 1).toString());

        // hubSeq + devCategory 를 이용해서 dev 테이블에서 목록 조회



        // 1. 응답 꾸미기
        /*
        devSeq / devName / devIdentifier
         */
        /*
        SimpleText text = new SimpleText();
        StringBuffer responseMsg = new StringBuffer("사용할 수 있는 모듈 목록 입니다.");
        responseMsg.append("모듈 번호  :  모듈 이름  :  식별명\n\n");
        for (int i = 0; i < dev.size(); i++) {
            responseMsg.append((i + 1) + " : " + dev.get(i).)
        }
         */



        // 2. 버튼 꾸미기



        // 3. redis 로 부터 mainOrder의 값을 가져온다.


        // 4. mainOrder 데이터 set



        // 5. redis에 mainOrder 데이터 저장



        return ResponseDtoVerTwo.builder().build();
    }
}
