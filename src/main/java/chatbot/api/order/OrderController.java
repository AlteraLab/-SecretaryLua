package chatbot.api.order;

import chatbot.api.common.domain.kakao.openbuilder.RequestDto;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseDtoVerTwo;
import chatbot.api.common.services.KakaoSimpleTextService;
import chatbot.api.order.domain.MainOrder;
import chatbot.api.order.domain.Response.ResponseDevInfo;
import chatbot.api.order.domain.SelectCmdOrder;
import chatbot.api.order.repository.MainOrderRepositoryImpl;
import chatbot.api.order.services.OrderResponseService;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@Slf4j
public class OrderController {

    @Autowired
    private OrderResponseService orderResponseService;

    @Autowired
    private KakaoSimpleTextService kakaoSimpleTextService;

    @Autowired
    private MainOrderRepositoryImpl mainOrderRepository;

    @Autowired
    private RestTemplate restTemplate;


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

        String providerId = requestDto.getUserRequest().getUser().getProperties().getAppUserId();
        log.info(requestDto.getUserRequest().getBlock().getName() + " >> " + requestDto.getUserRequest().getBlock().getId());
        log.info(requestDto.toString());

        int selectedCode = -100;

        // 문제는 버튼 블럭에서 버튼을 선택했을때, 그 값이 parentCode가 되면 안된다.
        String utterance = requestDto.getUserRequest().getUtterance();
        String strCheck = "";
        if (utterance.contains("버튼")) {
            strCheck = "버튼";
        }
        selectedCode = Integer.parseInt(requestDto.getUserRequest().getUtterance().replaceAll("[^0-9]", ""));
        log.info("selectedCode >> " + selectedCode);


        return orderResponseService.responserChildCmds(providerId, selectedCode, strCheck, "-");
    }



    // http://203.250.32.29:8083/control/dev/time
    @PostMapping("/control/dev/time")
    public ResponseDtoVerTwo controlTimeSet(@RequestBody RequestDto requestDto) {

        log.info("============ controlTimeSet ============");
        String providerId = requestDto.getUserRequest().getUser().getProperties().getAppUserId();
        int selectedCode = Integer.parseInt(requestDto.getUserRequest().getUtterance().replaceAll("[^0-9]", ""));
        log.info(requestDto.getUserRequest().getBlock().getName() + " >> " + requestDto.getUserRequest().getBlock().getId());
        log.info(requestDto.toString());
        log.info("selectedCode >> " + selectedCode);

        Object params = requestDto.getAction().getParams().get("datetime");
        log.info(params.toString());
        String setDateTime = null;
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(params.toString());
            setDateTime = jsonObject.get("value").toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("setDateTime >> " + setDateTime);

        String[] splitDateTime = setDateTime.split("T");

        String date = splitDateTime[0];
        String time = splitDateTime[1];

        date = date.replaceAll("-", "");
        time = time.replaceAll(":", "");

        // 시간 비교 코드
        String reqDateStr = date + time;
        reqDateStr = reqDateStr.substring(0, reqDateStr.length() - 2);
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMMddHHmm");
        Date curDate = new Date();
        Date reqDate = null;

        try {
            // 요청 시간을 date로 parsing 후 time 가져오기
            reqDate = dateFormat.parse(reqDateStr);
            // 현재 시간을 요청 시간의 형태로 format 후 time 가져오기
            curDate = dateFormat.parse(dateFormat.format(curDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
        long reqDateTime = reqDate.getTime();
        long curDateTime = curDate.getTime();

        // 분으로 표현
        long minute = (reqDateTime - curDateTime) / 60000;

        log.info("요청시간 : " + reqDate);
        log.info("현재시간 : " + curDate);
        log.info(minute + "분 차이");

        if((minute > 0) && (minute <= 300)) {
            String strMinute = null;
            strMinute = Long.toString(minute);
            log.info("정상적인 시간 등록");
            return orderResponseService.responserChildCmds(providerId, selectedCode, "", strMinute);
        } else {
            log.info("정상적이지 못한 시간 등록");
        }

        return orderResponseService.responserChildCmds(providerId, selectedCode, "", "-");
    }



    // http://203.250.32.29:8083/control/dev/button
    @PostMapping("/control/dev/button")
    public ResponseDtoVerTwo controlButton(@RequestBody RequestDto requestDto) {

        log.info("============ controlButton ============");
        String providerId = requestDto.getUserRequest().getUser().getProperties().getAppUserId();
        log.info(requestDto.getUserRequest().getBlock().getName() + " >> " + requestDto.getUserRequest().getBlock().getId());
        log.info(requestDto.toString());
        int selectedCode = Integer.parseInt(requestDto.getUserRequest().getUtterance().replaceAll("[^0-9]", ""));

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

        log.info("============ transferSelect ============");
        log.info(requestDto.getUserRequest().getBlock().getName() + " >> " + requestDto.getUserRequest().getBlock().getId());
        log.info(requestDto.toString());

        return kakaoSimpleTextService.makerTransferSelectCard();
    }


    // http://203.250.32.29:8083/control/transferResult
    @PostMapping("/control/transferResult")
    public ResponseDtoVerTwo controlTransferResult(@RequestBody RequestDto requestDto) {

        log.info("============ controlTransferResult ============");

        log.info(requestDto.getUserRequest().getBlock().getName() + " >> " + requestDto.getUserRequest().getBlock().getId());
        log.info(requestDto.toString());

        String providerId = requestDto.getUserRequest().getUser().getProperties().getAppUserId();
        String utterance = requestDto.getUserRequest().getUtterance();
        MainOrder reMainOrder = mainOrderRepository.find(providerId);

        if(utterance.equals("명령 전송")) {
            // 명령 전송
            String externalIp = reMainOrder.getSelectOrder().getExternalIp();
            int externalPort = reMainOrder.getSelectOrder().getExternalPort();
            String devMacAddr = reMainOrder.getSelectOrder().getDevMacAddr();
            SelectCmdOrder[] cmds = reMainOrder.getSelectOrder().getCmds();

            String url = new String("http://" + externalIp + ":" + externalPort + "/dev/" + devMacAddr);
            log.info("전달 URL >> " + url);


            Boolean result = restTemplate.postForObject(url, new Object(){public SelectCmdOrder[] cmd = cmds;}, Boolean.class);
            //restTemplate.postForObject(url, cmds, null);
            //Boolean result = true;
            // 명령을 전송했으니 redis에 있는 빌딩된 명령을 제거한다.
            mainOrderRepository.delete(providerId);
            StringBuffer msg = new StringBuffer("");
            if(result == true) {
                msg.append("명령이 전송되었습니다.\n\n");
                msg.append("또 다른 명령을 수행하고 싶으시다면 아래 슬롯을 올려 \"시바\" 버튼을 누르세요.");
            } else {
                msg.append("명령 전송이 실패하였습니다.\n\n");
                msg.append("또 다른 명령을 수행하고 싶으시다면 아래 슬롯을 올려 \"시바\" 버튼을 누르세요.");
            }
            return kakaoSimpleTextService.makerTransferCompleteCard(msg.toString());
        }

        // 사용자가 취소 명령을 눌렀다면!!!!!!!!!!!!!
        // 취소 선택 버튼 3 가지 경우의 수를 리턴한다.
        return kakaoSimpleTextService.makerCancleSelectCard();
    }



    @PostMapping("/control/cancleComplete")
    public ResponseDtoVerTwo controlCancleComplete(@RequestBody RequestDto requestDto) {

        log.info("============ controlCancleComplete ============");
        log.info(requestDto.getUserRequest().getBlock().getName() + " >> " + requestDto.getUserRequest().getBlock().getId());
        log.info(requestDto.toString());
        String providerId = requestDto.getUserRequest().getUser().getProperties().getAppUserId();

        // redis에 있는 모든 데이터 삭제
        mainOrderRepository.delete(providerId);

        return kakaoSimpleTextService.makerCancleCompleteCard();
    }



    @PostMapping("/control/test")
    public void controlTest(@RequestBody ResponseDevInfo responseDevInfo) {
        log.info(responseDevInfo.toString());
    }

    @GetMapping("/control/test/2")
    public Object controltTest2() {

        SelectCmdOrder[] cmds = new SelectCmdOrder[10];
        for(int i = 0; i < cmds.length; i++) {
            cmds[i] = new SelectCmdOrder(i + 1, i + 1);
            log.info(cmds[i].getCmdCode() + " " + cmds[i].getData());
        }

        return new Object(){
            public SelectCmdOrder[] cmd = cmds;
        };
    }
}