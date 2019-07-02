package chatbot.api.buildcode;

import chatbot.api.buildcode.domain.Build;
import chatbot.api.buildcode.domain.Path;
import chatbot.api.buildcode.domain.SelectedBtn;
import chatbot.api.buildcode.domain.response.HrdwrControlResult;
import chatbot.api.buildcode.repository.BuildRepository;
import chatbot.api.buildcode.services.TextBoxResponseService;
import chatbot.api.common.domain.ResponseDTO;
import chatbot.api.common.domain.kakao.openbuilder.RequestDTO;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseVerTwoDTO;
import chatbot.api.common.services.KakaoSimpleTextService;
import chatbot.api.common.services.TimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;


@RestController
@Slf4j
public class BuildController {

    @Autowired
    private TextBoxResponseService textBoxResponseService;

    @Autowired
    private KakaoSimpleTextService kakaoSimpleTextService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private BuildRepository buildRepository;


    // 사용자가 "시바"를 입력했을 때 호출되는 메소드
    @PostMapping("/build/hubs")
    public ResponseVerTwoDTO buildHubs(@RequestBody RequestDTO requestDto) {

        log.info("==================== build Hubs 시작 ====================");
        log.info(requestDto.toString());
        log.info("INFO >> Utterance -> " + requestDto.getUserRequest().getUtterance());

        String providerId = requestDto.getUserRequest().getUser().getProperties().getAppUserId();
        return textBoxResponseService.responserHubsBox(providerId);
    }

    @PostMapping("/TEST")
    public ResponseDTO TestMethod(@RequestBody RequestDTO requestDto) {

        log.info("==================== Test Method 시작 ====================");
        log.info(requestDto.toString());
        return ResponseDTO.builder().data(null).status(HttpStatus.OK).build();
    }

    // 사용자가 hub를 선택했을 때 호출되는 메소드
    @PostMapping("/build/hub")
    public ResponseVerTwoDTO buildHub(@RequestBody RequestDTO requestDto) {

        log.info("==================== build Hub 시작 ====================");
        log.info(requestDto.toString());

        String providerId = requestDto.getUserRequest().getUser().getProperties().getAppUserId();
        int hubSeq = Integer.parseInt(requestDto.getUserRequest().getUtterance().replaceAll("[^0-9]", ""));
        log.info("INFO >> Hub Seq -> " + hubSeq);

        return textBoxResponseService.responserHrdwrsBox(providerId, hubSeq);
    }


    // 사용자가 하드웨어를 선택했을 때 호출되는 메소드. 선택한 하드웨어의 Entry Box 에 속한 버튼들을 리턴
    @PostMapping("/build/hrdwr")
    public ResponseVerTwoDTO buildHrdwr(@RequestBody RequestDTO requestDto) {

        log.info("==================== build Hrdwr 시작 ====================");
        log.info(requestDto.toString());

        String providerId = requestDto.getUserRequest().getUser().getProperties().getAppUserId();
        int hrdwrSeq = Integer.parseInt(requestDto.getUserRequest().getUtterance().replaceAll("[^0-9]", ""));
        log.info("INFO >> Hrdwr Seq -> " + hrdwrSeq);

        return textBoxResponseService.responserEntryBox(providerId, hrdwrSeq);
    }


    // 사용자가 선택한 버튼이 btn 타입 버튼일 때!!
    @PostMapping("/build/btn")
    public ResponseVerTwoDTO buildBtn(@RequestBody RequestDTO requestDto) {

        log.info("==================== build Btn 시작 ====================");
        log.info("사용자가 선택한 버튼의 블록 아이디 속성값이 \"BTN_TEXTBOX\"일 때, 호출되는 메소드 입니다.");
        log.info(requestDto.toString());

        String providerId = requestDto.getUserRequest().getUser().getProperties().getAppUserId();
        int btnSeq = Integer.parseInt(requestDto.getUserRequest().getUtterance().replaceAll("[^0-9]", ""));
        log.info("INFO >> Btn Seq -> " + btnSeq);

        return textBoxResponseService.responserBtnBox(providerId, btnSeq);
    }



    @PostMapping("/build/time")
    public ResponseVerTwoDTO buildTime(@RequestBody RequestDTO requestDto) {

        log.info("==================== build Time 시작 ====================");
        log.info("사용자가 선택한 버튼의 블록 아이디 속성값이 \"TIME_TEXTBOX\"일 때, 호출되는 메소드 입니다.");
        log.info(requestDto.toString());

        String providerId = requestDto.getUserRequest().getUser().getProperties().getAppUserId();
        int btnSeq = Integer.parseInt(requestDto.getUserRequest().getUtterance().replaceAll("[^0-9]", ""));
        Object params = requestDto.getAction().getParams().get("datetime");

        log.info("INFO >> Btn Seq -> " + btnSeq);
        log.info("INFO >> Params -> " + params);

        TimeService timeService = new TimeService();
        int minute = timeService.convertFromDateTimeToMinute(params);

        return textBoxResponseService.responserBtnBoxFromTime(providerId, btnSeq, minute);
    }



    @PostMapping("/build/input")
    public ResponseVerTwoDTO buildInput(@RequestBody RequestDTO requestDto) {

        log.info("==================== build Input 시작 ====================");
        log.info("사용자가 선택한 버튼의 블록 아이디 속성값이 \"INPUT_TEXTBOX\"일 때, 호출되는 메소드 입니다.");
        log.info(requestDto.toString());

        String providerId = requestDto.getUserRequest().getUser().getProperties().getAppUserId();
        int btnSeq = Integer.parseInt(requestDto.getUserRequest().getUtterance().replaceAll("[^0-9]", ""));
        log.info("INFO >> Btn Seq -> " + btnSeq);

        return textBoxResponseService.responserInputBox(providerId, btnSeq);
    }



    @PostMapping("/build/input/context")
    public ResponseVerTwoDTO buildInputContext(@RequestBody RequestDTO requestDto) {

        log.info("==================== build Input Context 시작 ====================");
        log.info("사용자가 선택한 버튼의 블록 아이디 속성값이 \"INPUT_TEXTBOX_CONTEXT\"일 때, 호출되는 메소드 입니다.");
        log.info(requestDto.toString());

        String providerId = requestDto.getUserRequest().getUser().getProperties().getAppUserId();
        int inputValue = Integer.parseInt(requestDto.getUserRequest().getUtterance().replaceAll("[^0-9]", ""));
        log.info("INFO >> 사용자가 입력한 inputValue -> " + inputValue);

        return textBoxResponseService.responserBtnBoxFromInputContext(providerId, inputValue);
    }



    @PostMapping("/complete/builded/codes")
    public ResponseVerTwoDTO transferCompleteBuildedCodes(@RequestBody RequestDTO requestDto) {

        log.info("==================== Transfer Complete Builded Codes 시작 ====================");
        log.info("사용자가 선택한 버튼이 \"예\" 버튼 이여서, 빌드된 코드를 전송할 때 호출되는 메소드 입니다.");
        log.info(requestDto.toString());

        String providerId = requestDto.getUserRequest().getUser().getProperties().getAppUserId();
        String utterance = requestDto.getUserRequest().getUtterance();
        Build reBuild = buildRepository.find(providerId);

        if(utterance.equals("명령 전송")) {
            Path path = reBuild.getPath();

            log.info("INFO >> PATH -> " + path.toString());

            String externalIp = path.getExternalIp();
            int externalPort = path.getExternalPort();
            String hrdwrMacAddr = path.getHrdwrMacAddr();

            ArrayList<SelectedBtn> btns = reBuild.getSelectedBtns();
            SelectedBtn[] arrBtns = btns.toArray(new SelectedBtn[btns.size()]);
            log.info("INFO >> Builed Code 목록 -------");
            for(SelectedBtn temp : arrBtns) {
                log.info("- " + temp.toString());
            }

            String url = new String("http://" + externalIp + ":" + externalPort + "/dev/" + hrdwrMacAddr);
            log.info("INFO >> URL -> " + url);


            // 밑에 임시 주석
            HrdwrControlResult result = restTemplate.postForObject(url, new Object(){
                    public String requester_id = reBuild.getHProviderId();
                    public SelectedBtn[] cmd = arrBtns;
                }, HrdwrControlResult.class);
            //HrdwrControlResult result = new HrdwrControlResult();
            result.setStatus(true);
            log.info("INFO >> Result 결과 -> " + result.toString());

            buildRepository.delete(providerId);

            StringBuffer msg = new StringBuffer("");
            if(result.isStatus() == true) {
                msg.append("명령 전송이 성공적으로 수행 되었습니다.\n\n");
            } else if (result.isStatus() == false) {
                msg.append("명령 전송이 실패하였습니다.\n\n");
            }
            msg.append("또 다른 명령을 수행하고 싶으시다면 아래 슬롯을 올려 \"시바\" 버튼을 누르세요.");
            return kakaoSimpleTextService.makerTransferCompleteCard(msg.toString());
        }

        // 사용자가 취소 명령을 눌렀다면
        // 취소 선택 버튼 3가지 경우의 수를 리턴한다.
        return kakaoSimpleTextService.makerCancleSelectCard();
    }



    @PostMapping("/builed/codes")
    public ResponseVerTwoDTO transferCodes(@RequestBody RequestDTO requestDto) {

        log.info("============ Transfer Codes 시작 ============");
        log.info("중간에 \"전송\" 버튼을 눌렀을때, 빌딩된 명령을 전송하는 메소드");
        log.info(requestDto.toString());

        return kakaoSimpleTextService.makerTransferSelectCard();
    }
}
