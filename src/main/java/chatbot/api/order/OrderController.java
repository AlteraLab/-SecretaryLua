package chatbot.api.order;

import chatbot.api.common.domain.kakao.openbuilder.RequestDto;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseDtoVerTwo;
import chatbot.api.common.services.KakaoSimpleTextService;
import chatbot.api.order.domain.MainOrder;
import chatbot.api.order.repository.MainOrderRepositoryImpl;
import chatbot.api.order.services.OrderResponseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class OrderController {

    @Autowired
    private OrderResponseService orderResponseService;

    @Autowired
    private KakaoSimpleTextService kakaoSimpleTextService;

    @Autowired
    private MainOrderRepositoryImpl mainOrderRepository;


    // "시바" 를 발화했을때 호출되는 메서드
    @PostMapping("/control/hubs")
    public ResponseDtoVerTwo controlHubs(@RequestBody RequestDto requestDto) {

        log.info("============ ControlHubs ============");
        log.info(requestDto.toString());
        String providerId = requestDto.getUserRequest().getUser().getProperties().getAppUserId();
        return orderResponseService.responserHubs(providerId);
    }


    // hub를 선택했을때 호출되는 메서드
    @PostMapping("/control/hub/id")
    public ResponseDtoVerTwo controlHubId(@RequestBody RequestDto requestDto) {

        log.info("============ ControlHubId ============");
        // In-Memory에 사용자의 appUserId (provider Id)가 없다면, "명령 절차를 준수하세요!" 라는 메시지 전송
        String providerId = requestDto.getUserRequest().getUser().getProperties().getAppUserId();
        String hubSequence = requestDto.getUserRequest().getUtterance().replaceAll("[^0-9]", "");
        log.info(requestDto.toString());
        int hubSeq = Integer.parseInt(hubSequence);
        log.info("hubSeq : " + hubSeq);
        return orderResponseService.responserDevs(providerId, hubSeq);
    }


    // module을 선택했을때 호출되는 메서드
    @PostMapping("/control/dev/id")
    public ResponseDtoVerTwo controlModuleId(@RequestBody RequestDto requestDto) {

        log.info("============ ControlModuleId ============");
        // In-Memory에 사용자의 appUserId (provider Id)가 없다면, "명령 절차를 준수하세요!" 라는 메시지 전송
        String providerId = requestDto.getUserRequest().getUser().getProperties().getAppUserId();
        String devSequence = requestDto.getUserRequest().getUtterance().replaceAll("[^0-9]", "");
        log.info(requestDto.toString());
        int devSeq = Integer.parseInt(devSequence);
        log.info("devSeq : " + devSeq);
        return orderResponseService.responserCmds(providerId, devSeq);
    }


    // http://203.250.32.29:8083/control/code/list
    @PostMapping("/control/code/list")
    public ResponseDtoVerTwo controlCodeList(@RequestBody RequestDto requestDto) {

        log.info("============ controlCodeList ============");
/*        log.info("\n============ TEST TEST TEST TEST ============\n");
        MainOrder testMainOrder = mainOrderRepository.find(requestDto.getUserRequest().getUser().getProperties().getAppUserId());
        log.info("DevMacAddr    >> " + testMainOrder.getSelectOrder().getDevMacAddr());
        log.info("ExternalIp    >> " + testMainOrder.getSelectOrder().getExternalIp());
        log.info("ExternalPort  >> " + testMainOrder.getSelectOrder().getExternalPort());
        if(testMainOrder.getCmdOrderList() == null) {
            log.info("NULL NULL NULL NULL NULL NULL NULL NULL ");
        }
        log.info("Cmds().length >> " + testMainOrder.getCmdOrderList().length);
        log.info("Cmds >> " + testMainOrder.getCmdOrderList());
        for(int i = 0; i < testMainOrder.getCmdOrderList().length; i++) {
            log.info("Cmd[" + (i + 1) + "] >> " + testMainOrder.getCmdOrderList()[i]);
        }
        log.info("\n============ TEST TEST TEST TEST ============\n");*/

        String providerId = requestDto.getUserRequest().getUser().getProperties().getAppUserId();
        log.info(requestDto.getUserRequest().getBlock().getName() + " >> " + requestDto.getUserRequest().getBlock().getId());
        log.info(requestDto.toString());

        int selectedCode = -100;

        // 문제는 버튼 블럭에서 버튼을 선택했을때, 그 값이 parentCode가 되면 안된다.
        String utterance = requestDto.getUserRequest().getUtterance();
        String strCheck = "";
        if (utterance.contains("버튼")) {
            MainOrder reMainOrder = mainOrderRepository.find(providerId);
            selectedCode = reMainOrder.getCurrentParentCode();
            strCheck = "버튼";
            // 선택된 버튼을 초기화 시켜주는 버튼도 필요함. selectOrder 에서
        } else {
            selectedCode = Integer.parseInt(requestDto.getUserRequest().getUtterance().replaceAll("[^0-9]", ""));
        }

        log.info("selectedCode >> " + selectedCode);


        return orderResponseService.responserChildCmds(providerId, selectedCode, strCheck);
    }


    // http://203.250.32.29:8083/control/dev/time
    @PostMapping("/control/dev/time")
    public ResponseDtoVerTwo controlTimeSet(@RequestBody RequestDto requestDto) {

        log.info("============ controlTimeSet ============");
        String providerId = requestDto.getUserRequest().getUser().getProperties().getAppUserId();
        log.info(requestDto.getUserRequest().getBlock().getName() + " >> " + requestDto.getUserRequest().getBlock().getId());
        log.info(requestDto.toString());
        int selectedCode = Integer.parseInt(requestDto.getUserRequest().getUtterance().replaceAll("[^0-9]", ""));

        log.info("selectedCode >> " + selectedCode);

        return orderResponseService.responserChildCmds(providerId, selectedCode, "");
    }


    // http://203.250.32.29:8083/control/dev/button
    @PostMapping("/control/dev/button")
    public ResponseDtoVerTwo controlButton(@RequestBody RequestDto requestDto) {

        log.info("============ controlButton ============");
        String providerId = requestDto.getUserRequest().getUser().getProperties().getAppUserId();
        log.info(requestDto.getUserRequest().getBlock().getName() + " >> " + requestDto.getUserRequest().getBlock().getId());
        log.info(requestDto.toString());
        int selectedCode = Integer.parseInt(requestDto.getUserRequest().getUtterance().replaceAll("[^0-9]", ""));
        ;
        log.info("selectedCode >> " + selectedCode);

        return orderResponseService.responserButtonCmds(providerId, selectedCode);
    }


    // http://203.250.32.29:8083/control/dev/directInput
    @PostMapping("/control/dev/directInput")
    public ResponseDtoVerTwo controlDirectInput(@RequestBody RequestDto requestDto) {

        log.info("============ controlDirectInput ============");
        String providerId = requestDto.getUserRequest().getUser().getProperties().getAppUserId();
        log.info(requestDto.getUserRequest().getBlock().getName() + " >> " + requestDto.getUserRequest().getBlock().getId());
        log.info(requestDto.toString());
        int selectedCode = Integer.parseInt(requestDto.getUserRequest().getUtterance().replaceAll("[^0-9]", ""));

        log.info("selectedCode >> " + selectedCode);

        return orderResponseService.responserDirectInput(providerId, selectedCode);
    }


    // http://203.250.32.29:8083/control/dev/directInput/context
    @PostMapping("/control/dev/directInput/context")
    public ResponseDtoVerTwo controlDirectInputContext(@RequestBody RequestDto requestDto) {

        log.info("============ controlDirectInputContext ============");
        String providerId = requestDto.getUserRequest().getUser().getProperties().getAppUserId();
        log.info(requestDto.getUserRequest().getBlock().getName() + " >> " + requestDto.getUserRequest().getBlock().getId());
        log.info(requestDto.toString());

        int dirInUtterance = Integer.parseInt(requestDto.getUserRequest().getUtterance().replaceAll("[^0-9]", ""));

        log.info("dirInUtterance >> " + dirInUtterance);

        return orderResponseService.responserChildCmdsAfterDirInput(providerId, dirInUtterance);
    }


    // http://203.250.32.29:8083/control/select
    @PostMapping("/control/select")
    public ResponseDtoVerTwo controlSendSelect(@RequestBody RequestDto requestDto) {

        log.info("============ controlExit ============");
        log.info(requestDto.getUserRequest().getBlock().getName() + " >> " + requestDto.getUserRequest().getBlock().getId());
        log.info(requestDto.toString());

        // 명령을 허브로 전송한다.

        // 명령을 제거한다.

        // "시바" 버튼이 달린 메시지를 전송한다.
        return kakaoSimpleTextService.makerTransferSelectCard();
    }


    // http://203.250.32.29:8083/control/transferResult
    @PostMapping("/control/transferResult")
    public ResponseDtoVerTwo controlTransferResult(@RequestBody RequestDto requestDto) {

        log.info("============ controlTransferResult ============");
        log.info(requestDto.getUserRequest().getBlock().getName() + " >> " + requestDto.getUserRequest().getBlock().getId());
        log.info(requestDto.toString());

        String utterance = requestDto.getUserRequest().getUtterance();

        if(utterance.equals("명령 전송")) {
            // 명령 전송

            // redis에 있는 빌딩된 명령을 제거한다.

            return kakaoSimpleTextService.makerTransferCompleteCard();
        }

        // 사용자가 취소 명령을 눌렀다면!!!!!!!!!!!!!
        // 취소 선택 버튼 3가지 경우의 수를 리턴한다.
        return kakaoSimpleTextService.makerCancleSelectCard();
    }



    @PostMapping("/control/cancleComplete")
    public ResponseDtoVerTwo controlCancleComplete(@RequestBody RequestDto requestDto) {

        log.info("============ controlCancleComplete ============");
        log.info(requestDto.getUserRequest().getBlock().getName() + " >> " + requestDto.getUserRequest().getBlock().getId());
        log.info(requestDto.toString());

        // redis에 있는 모든 데이터 삭제
        return kakaoSimpleTextService.makerCancleCompleteCard();
    }
}