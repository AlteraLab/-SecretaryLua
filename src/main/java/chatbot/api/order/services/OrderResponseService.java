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
import chatbot.api.order.Repository.HubOrderRepositoryImpl;
import chatbot.api.order.Repository.SelectOrderRepositoryImpl;
import chatbot.api.order.domain.HubOrderDto;
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

    private HubOrderRepositoryImpl hubOrderRepository;

    private SelectOrderRepositoryImpl selectOrderRepository;



    // 사용자가 이용할 수 있는 허브들 중에서 발화한 모듈 타입이 부착된 허브 목록을 responser
    public ResponseDtoVerTwo responserHubsAboutDevType(String providerId, String devCategory) {
        
        ResponseDtoVerTwo responseDtoVerTwo = new ResponseDtoVerTwo();

        if(providerId == null) {
            return responseDtoVerTwo = kakaoBasicCardResponseService.responserRequestPreSignUp();
        }

        UserInfoDto user = userMapper.getUserByProviderId(providerId).get();
        if(user == null) {
            return responseDtoVerTwo = kakaoSimpleTextResponseService.responserShortMsg("등록되지 않은 사용자 입니다.");
        }

        if(!providerId.equals(user.getProviderId())) {
            return responseDtoVerTwo = kakaoSimpleTextResponseService.responserShortMsg("아이디가 동일하지 않습니다.");
        }
        
        // 명령 시나리오대로 코드 작성

        // 1. 데이터베이스에서 사용자가 사용할 수 있는 허브들 중에서 사용자 발화와 일치하는 모듈 타입이 부착된 허브 목록을 get
        log.info(user.toString());
        log.info(devCategory);
        ArrayList<HubInfoDto> hubs = hubmapper.getHubInfoByDevCategory(user.getUserId(), devCategory);
        log.info(hubs.toString());

        if(hubs == null) {
            return responseDtoVerTwo = kakaoSimpleTextResponseService.responserShortMsg("사용할 수 있는 모듈이 부착된 허브가 없습니다.");
        }

        // 2. "응답" 꾸미기
        SimpleText simpleTextVo = new SimpleText();
        StringBuffer responseMsg = new StringBuffer("사용할 수 있는 허브 목록 입니다.\n\n");
        responseMsg.append("허브 번호  :  허브 이름\n\n");
        for(int i = 0; i < hubs.size(); i++) {
            responseMsg.append((i + 1) + " : " + hubs.get(i).getHubName() + "\n");
        }
        responseMsg.append("\n버튼을 클릭해주세요.");

        simpleTextVo.setText(responseMsg.toString());

        ComponentSimpleText simpleText = new ComponentSimpleText();
        simpleText.setSimpleText(simpleTextVo);

        ArrayList<Object> outputs = new ArrayList<Object>();
        outputs.add(simpleText);

        // 3. "버튼" 꾸미기
        ArrayList<QuickReply> quickReplies = new ArrayList<QuickReply>();
        QuickReply[] quickReply = new QuickReply[hubs.size()];
        for (int i = 0; i < hubs.size(); i++) {
            QuickReply quick = QuickReply.builder()
                    //.label(Long.toString(hubs.get(i).getHubId()))
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

        /*
        4. Redis에 데이터 저장
        hashMap 만들어서,
        Key : appUserId
        value :
            devCategory (다음에 허브에 연결된 모듈들을 조회해올때 사용)
            hubSeq
            hubId
            explicitIp (전송 경로용)
            explicitPort (전송 경로용)
         */
        HubOrderDto[] hubOrderDtos = new HubOrderDto[hubs.size()];
        for (int i = 0; i < hubs.size(); i++) {
            hubOrderDtos[i] = HubOrderDto.builder()
                                .hubSeq(i + 1)
                                .hubId(hubs.get(i).getHubId())
                                .explicitIp(hubs.get(i).getExternalIp())
                                .explicitPort(hubs.get(i).getExternalPort())
                                .build();
            hubOrderRepository.save(providerId, hubOrderDtos[i]);
            log.info((i + 1) + hubOrderDtos[i].toString());
        }

        // selectOrderVo (유지되어야 할 데이터를 담은 VO) 도 In-Memory 에 저장
        SelectOrderVo selectOrderVo = new SelectOrderVo();
        selectOrderVo.setDevCategory(devCategory);
        selectOrderVo.setProviderId(providerId);
        selectOrderRepository.save(selectOrderVo);

        // 5. 사용자에게 응답.
        return responseDtoVerTwo;
    }



    public ResponseDtoVerTwo responserDevsAboutSpecificHub(String providerId, int hubSeq) {

        //
        ArrayList<HubOrderDto> hubList = hubOrderRepository.findAll(providerId);
        log.info("2-1. " + hubList.toString());
        if(hubList.size() == 0) {
            return kakaoSimpleTextResponseService.responserShortMsg("명령 전송 절차를 준수 해주세요.");
        }

        for(int i = 0; i < hubList.size(); i++) {
            if(hubList.get(i).getHubSeq() == hubSeq) {
                SelectOrderVo selectOrderVo = selectOrderRepository.find(providerId);
                selectOrderVo.setHubId(hubList.get(i).getHubId());
                selectOrderVo.setExternalIp(hubList.get(i).getExplicitIp());
                selectOrderVo.setExternalPort(hubList.get(i).getExplicitPort());
                selectOrderRepository.save(selectOrderVo);
                break;
            }
        }
        SelectOrderVo selectOrderVo = selectOrderRepository.find(providerId);
        log.info(selectOrderVo.toString());


        log.info("삭제중");
        for(int i = 0; i < hubList.size(); i++) {
            hubOrderRepository.deleteAllByProviderId(providerId, hubList.get(i).getHubId());
        }


        ArrayList<HubOrderDto> hubLists = hubOrderRepository.findAll(providerId);
        log.info("2-2. " + hubLists.toString());
        return kakaoSimpleTextResponseService.responserShortMsg("삭제 잘됨.");
    }
}
