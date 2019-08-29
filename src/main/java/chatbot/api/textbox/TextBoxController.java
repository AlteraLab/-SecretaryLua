package chatbot.api.textbox;

import chatbot.api.common.services.KakaoBasicCardService;
import chatbot.api.common.services.RestTemplateService;
import chatbot.api.common.services.TimeService;
import chatbot.api.textbox.domain.*;
import chatbot.api.textbox.domain.path.Path;
import chatbot.api.textbox.domain.response.HrdwrControlResult;
import chatbot.api.textbox.domain.transfer.CmdList;
import chatbot.api.textbox.repository.BuildRepository;
import chatbot.api.textbox.services.BuildSaveService;
import chatbot.api.textbox.services.TextBoxResponseService;
import chatbot.api.common.domain.kakao.openbuilder.RequestDTO;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseVerTwoDTO;
import chatbot.api.common.services.KakaoSimpleTextService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.ArrayList;

import static chatbot.api.textbox.utils.TextBoxConstants.EVERY_DAY_EXECUTE;
import static chatbot.api.textbox.utils.TextBoxConstants.EVERY_WEEK_EXECUTE;
import static chatbot.api.textbox.utils.TextBoxConstants.ONLY_ONE_EXECUTE;

@RestController
@Slf4j
public class TextBoxController {

    @Autowired
    private TextBoxResponseService textBoxResponseService;

    @Autowired
    private KakaoSimpleTextService kakaoSimpleTextService;

    @Autowired
    private BuildRepository buildRepository;

    @Autowired
    private TimeService timeService;

    @Autowired
    private BuildSaveService buildSaveService;

    @Autowired
    private RestTemplateService restTemplateService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private KakaoBasicCardService kakaoBasicCardService;


    // from "시바" to hubs box
    @PostMapping("/textbox/hubs")
    public ResponseVerTwoDTO textBoxHubsFromUtterance(@RequestBody RequestDTO requestDto) {
        log.info("\n\n"); log.info("==================== from \"시바\" to hubs box 시작 ====================");
        log.info(requestDto.toString());
        log.info("INFO >> Utterance -> " + requestDto.getUserRequest().getUtterance());
        String providerId = requestDto.getUserRequest().getUser().getProperties().getAppUserId();
        log.info(providerId + "");
        return textBoxResponseService.responserHubsBox(providerId);
    }


    // from hubs box to hrdwrs box
    @PostMapping("/textbox/hrdwrs")
    public ResponseVerTwoDTO textBoxHrdwrsFromHubs(@RequestBody RequestDTO requestDto) {
        log.info("\n\n"); log.info("==================== from hubs box to hrdwrs box 시작 ====================");
        log.info(requestDto.toString());

        String providerId = requestDto.getUserRequest().getUser().getProperties().getAppUserId();
        int hubSeq = Integer.parseInt(requestDto.getUserRequest().getUtterance().replaceAll("[^0-9]", ""));
        log.info("INFO >> Hub Seq -> " + hubSeq);

        return textBoxResponseService.responserHrdwrsBox(providerId, hubSeq);
    }


    // from hrdwrs box to entry box
    @PostMapping("/textbox/entry")
    public ResponseVerTwoDTO textBoxEntryFromHrdwrs(@RequestBody RequestDTO requestDto) {
        log.info("\n\n"); log.info("==================== from hrdwrs box to entry box 시작 ====================");
        log.info(requestDto.toString());

        String providerId = requestDto.getUserRequest().getUser().getProperties().getAppUserId();
        Integer hrdwrSeq = null;

        if(!(requestDto.getUserRequest().getUtterance().contains("취소"))) {
            hrdwrSeq = Integer.parseInt(requestDto.getUserRequest().getUtterance().replaceAll("[^0-9]", ""));
        }
        log.info("INFO >> Hrdwr Seq -> " + hrdwrSeq);

        return textBoxResponseService.responserEntryBox(providerId, hrdwrSeq);
    }


    // 1. (제어) 시나리오
    @PostMapping("/textbox/end/entry")
    public ResponseVerTwoDTO textBoxEndFrEntry(@RequestBody RequestDTO requestDTO) {
        // 1. 사용자가 선택한 버튼을 저장해야 한다
        //      1-1. 사용자가 선택한 버튼의 버튼 타입을 저장해야 한다
        //      1-2. 사용자가 선택한 버튼의 이벤트 코드를 저장해야 한다
        log.info("============== textBox (End_Entry) ==============");
        log.info(requestDTO.toString());

        String providerId = requestDTO.getUserRequest().getUser().getProperties().getAppUserId();
        String blockId = requestDTO.getUserRequest().getBlock().getId();
        Integer btnIdx = Integer.parseInt(requestDTO.getUserRequest().getUtterance().replaceAll("[^0-9]", ""));

        log.info("Block Id -> " + blockId);
        log.info("Button Index -> " + btnIdx);

        return textBoxResponseService.responserEndBoxFrEntry(providerId, btnIdx);
    }


    // 2. (제어->시간->동적) 시나리오
    @PostMapping("/textbox/dynamic/time/entry")
    public ResponseVerTwoDTO textBoxDynamicFrTimeFrEntry(@RequestBody RequestDTO requestDTO) {
        // 1. 사용자가 선택한 버튼을 저장해야 한다
        //      1-1. 사용자가 선택한 버튼의 버튼 타입을 저장해야 한다
        //      1-2. 사용자가 선택한 버튼의 이벤트 코드를 저장해야 한다
        // 2. timestamp 를 저장해야 한다

        log.info("\n\n"); log.info("============== textBox (Dynamic_Time_Entry) ==============");
        log.info(requestDTO.toString());

        String providerId = requestDTO.getUserRequest().getUser().getProperties().getAppUserId();
        String blockId = requestDTO.getUserRequest().getBlock().getId();
        Integer btnIdx = Integer.parseInt(requestDTO.getUserRequest().getUtterance().replaceAll("[^0-9]", ""));

        Object dateTimeParams = requestDTO.getAction().getParams().get("datetime");
        Timestamp timeStamp = timeService.convertTimeStampFromObject(dateTimeParams);

        log.info("Block Id -> " + blockId);
        log.info("Button Index -> " + btnIdx);
        log.info("DataTime -> " + dateTimeParams);
        log.info("TimeStamp -> " + timeStamp);

        // 사용자에게 보여줄 Dynamic Box 를 사용자에게 보여줘야 한다.
        return textBoxResponseService.responserDynamicBoxFrTimeFrEntry(providerId, btnIdx, timeStamp);
    }
    @PostMapping("/textbox/end/dynamic/time/entry")
    public ResponseVerTwoDTO textBoxEndFrDynamicFrTimeFrEntry(@RequestBody RequestDTO requestDTO) {
        // 1. 동적 입력 데이터를 Additonal 에 추가해야 한다
        // 2. 하위 박스가 있는지 없는지 체크한다
        //      2-1. 하위 박스가 없다면 -> End Box 라는 의미이니까, 사용자에게 명령을 전달할거냐고 묻는다
        //      2-2. 하위 박스가 있다면 -> End Box 가 아니라는 의미이니까, Entry Box 때 했던것과 비슷한 작업을 수행한다

        log.info("============== textBox (End_Dynamic_Time_Entry) ==============");
        log.info(requestDTO.toString());

        String providerId = requestDTO.getUserRequest().getUser().getProperties().getAppUserId();
        String blockId = requestDTO.getUserRequest().getBlock().getId();
        Long dynamicValue = Long.parseLong(requestDTO.getUserRequest().getUtterance().replaceAll("[^0-9]", ""));

        log.info("Block Id -> " + blockId);
        log.info("INFO >> 사용자가 입력한 dynamicValue -> " + dynamicValue);
        return textBoxResponseService.responserEndBoxFrDynamicFrTimeFrEntry(providerId, dynamicValue);
    }


    // 3. (제어->동적->시간) 시나리오
    @PostMapping("/textbox/_dynamic/entry")
    public ResponseVerTwoDTO textBox_DynamicFrEntry(@RequestBody RequestDTO requestDTO) {
        // 1. 사용자가 선택한 버튼을 저장해야 한다
        //      1-1. 사용자가 선택한 버튼의 버튼 타입을 저장해야 한다
        //      1-2. 사용자가 선택한 버튼의 이벤트 코드를 저장해야 한다
        // 2. 동적 입력 박스를 사용자에게 보여줘야 한다.

        log.info("============== textBox (_Dynamic_Entry) ==============");
        log.info(requestDTO.toString());

        String providerId = requestDTO.getUserRequest().getUser().getProperties().getAppUserId();
        String blockId = requestDTO.getUserRequest().getBlock().getId();
        Integer btnIdx = Integer.parseInt(requestDTO.getUserRequest().getUtterance().replaceAll("[^0-9]", ""));

        return textBoxResponseService.responserUDynamicBoxFrEntry(providerId, btnIdx); // U == underbar
    }
    @PostMapping("/textbox/end/time/_dynamic/entry")
    public ResponseVerTwoDTO textBoxEndFrTimeFr_DynamicFrEntry(@RequestBody RequestDTO requestDTO) {
        // 1. timestamp 를 저장해야 한다
        // 2. 동적 입력 데이터를 저장해야 한다
        // 3. 하위 박스가 있는지 없는지 체크한다
        //      3-1. 하위 박스가 없다면 -> End Box 라는 의미이니까, 사용자에게 명령을 전달할거냐고 묻는다
        //      3-2. 하위 박스가 있다면 -> End Box 가 아니라는 의미이니까, Entry Box 때 했던것과 비슷한 작업을 수행한다

        log.info("============== textBox (End_Time__Dynamic_Entry) ==============");
        log.info(requestDTO.toString());

        String blockId = requestDTO.getUserRequest().getBlock().getId();
        String providerId = requestDTO.getUserRequest().getUser().getProperties().getAppUserId();
        Long dynamicValue = Long.parseLong(requestDTO.getUserRequest().getUtterance().replaceAll("[^0-9]", ""));
        Object dateTimeParams = requestDTO.getAction().getParams().get("datetime");
        Timestamp timeStamp = timeService.convertTimeStampFromObject(dateTimeParams);

        log.info("Block Id -> " + blockId);
        log.info("INFO >> 사용자가 입력한 dynamicValue -> " + dynamicValue);
        log.info("DataTime -> " + dateTimeParams);
        log.info("TimeStamp -> " + timeStamp);

        return textBoxResponseService.responserEndBoxFrTimeFrUDynamicFrEntry(providerId, dynamicValue, timeStamp);
    }


    // 4. (제어->시간) 시나리오
    @PostMapping("/textbox/end/time/entry")
    public ResponseVerTwoDTO textBoxEndFrTimeFrEntry(@RequestBody RequestDTO requestDTO) {
        // 1. 사용자가 선택한 버튼을 저장해야 한다
        //      1-1. 사용자가 선택한 버튼의 버튼 타입을 저장해야 한다
        //      1-2. 사용자가 선택한 버튼의 이벤트 코드를 저장해야 한다
        // 2. timestamp 를 저장해야 한다
        // 3. 하위 박스가 있는지 없는지 체크한다
        //      3-1. 하위 박스가 없다면 -> End Box 라는 의미이니까, 사용자에게 명령을 전달할거냐고 묻는다
        //      3-2. 하위 박스가 있다면 -> End Box 가 아니라는 의미이니까, Entry Box 때 했던것과 비슷한 작업을 수행한다

        log.info("============== textBox (End_Time_Entry) ==============");
        log.info(requestDTO.toString());

        String blockId = requestDTO.getUserRequest().getBlock().getId();
        String providerId = requestDTO.getUserRequest().getUser().getProperties().getAppUserId();
        Integer btnIdx = Integer.parseInt(requestDTO.getUserRequest().getUtterance().replaceAll("[^0-9]", ""));
        Object dateTimeParams = requestDTO.getAction().getParams().get("datetime");
        Timestamp timeStamp = timeService.convertTimeStampFromObject(dateTimeParams);

        log.info("Block Id -> " + blockId);
        log.info("Button Index -> " + btnIdx);
        log.info("DataTime -> " + dateTimeParams);
        log.info("TimeStamp -> " + timeStamp);

        return textBoxResponseService.responserEndBoxFrTimeFrEntry(providerId, btnIdx, timeStamp);
    }


    // 5. (제어->동적) 시나리오
    @PostMapping("/textbox/dynamic/entry")
    public ResponseVerTwoDTO textBoxDynamicFrEntry(@RequestBody RequestDTO requestDTO) {
        // 1. 사용자가 선택한 버튼을 저장해야 한다
        //      1-1. 사용자가 선택한 버튼의 버튼 타입을 저장해야 한다
        //      1-2. 사용자가 선택한 버튼의 이벤트 코드를 저장해야 한다
        // 2. Cur Box 를 조정해야 한다

        log.info("============== textBox (Dynamic_Entry) ==============");
        log.info(requestDTO.toString());

        String blockId = requestDTO.getUserRequest().getBlock().getId();
        String providerId = requestDTO.getUserRequest().getUser().getProperties().getAppUserId();
        Integer btnIdx = Integer.parseInt(requestDTO.getUserRequest().getUtterance().replaceAll("[^0-9]", ""));

        log.info("Block Id -> " + blockId);
        log.info("Button Index -> " + btnIdx);

        return textBoxResponseService.responserDynamicBoxFrEntry(providerId, btnIdx);
    }
    @PostMapping("/textbox/end/dynamic/entry")
    public ResponseVerTwoDTO textBoxEndFrDynamicFrEntry(@RequestBody RequestDTO requestDTO) {
        // 1. 동적 입력 데이터를 저장해야 한다
        // 2. curBox 를 조정해야 한다
        // 3. 하위 박스가 있는지 없는지 체크한다
        //      3-1. 하위 박스가 없다면 -> End Box 라는 의미이니까, 사용자에게 명령을 전달할거냐고 묻는다
        //      3-2. 하위 박스가 있다면 -> End Box 가 아니라는 의미이니까, Entry Box 때 했던것과 비슷한 작업을 수행한다

        log.info("============== textBox (End_Dynamic_Entry) ==============");
        log.info(requestDTO.toString());

        String blockId = requestDTO.getUserRequest().getBlock().getId();
        String providerId = requestDTO.getUserRequest().getUser().getProperties().getAppUserId();
        Long dynamicValue = Long.parseLong(requestDTO.getUserRequest().getUtterance().replaceAll("[^0-9]", ""));

        log.info("Block Id -> " + blockId);
        log.info("INFO >> 사용자가 입력한 dynamicValue -> " + dynamicValue);

        return textBoxResponseService.responserEndBoxFrDynamicFrEntry(providerId, dynamicValue);
    }



    // 센싱 조회
    @PostMapping("/textbox/lookup/sensing")
    public ResponseVerTwoDTO textBoxLookUpSensingFromAny(@RequestBody RequestDTO requestDTO) {
        log.info("\n\n==================== from LookUp Sensing box to control box 시작 ====================");
        String providerId = requestDTO.getUserRequest().getUser().getProperties().getAppUserId();
        Integer btnIdx = Integer.parseInt(requestDTO.getUserRequest().getUtterance().replaceAll("[^0-9]", ""));
        log.info(requestDTO.toString());

        return textBoxResponseService.responserSensingBox(providerId, btnIdx);
    }


    // 디바이스 상태 조회
    @PostMapping("/textbox/lookup/device")
    public ResponseVerTwoDTO textBoxLookUpDeviceFromAny(@RequestBody RequestDTO requestDTO) {
        log.info("\n\n==================== from LookUp Device box to control box 시작 ====================");
        String providerId = requestDTO.getUserRequest().getUser().getProperties().getAppUserId();
        Integer btnIdx = Integer.parseInt(requestDTO.getUserRequest().getUtterance().replaceAll("[^0-9]", ""));
        log.info(requestDTO.toString());

        return textBoxResponseService.responserDevInfoBox(providerId, btnIdx);
    }


    // 예약 목록 조회
    @PostMapping("/textbox/lookup/reservation")
    public ResponseVerTwoDTO textBoxLookUpReservationFromAny(@RequestBody RequestDTO requestDTO) {
        log.info("\n\n"); log.info("==================== from entry box to LookUp Reservation box 시작 ====================");
        String providerId = requestDTO.getUserRequest().getUser().getProperties().getAppUserId();
        String blockId = requestDTO.getUserRequest().getBlock().getId();
        log.info("Block Id -> " + blockId);
        log.info(requestDTO.toString());
        return textBoxResponseService.responserReservationListBox(providerId);
    }


    // 예약 리스트 중 취소 항목이 있을때 호출되는 메소드
    @PostMapping("/textbox/reservation/deletion/result")
    public ResponseVerTwoDTO textBoxReservationDeletionResult(@RequestBody RequestDTO requestDTO) {
        // 5d3e35ddffa7480001de2d1c
        log.info("\n\n"); log.info("==================== from LookUp Reservation box to Reservation Delete Result 시작 ====================");
        log.info(requestDTO.toString());
        String providerId = requestDTO.getUserRequest().getUser().getProperties().getAppUserId();

        // 사용자가 취소하기러 한 명령이 무엇인지 뽑아낸다
        Integer resIdForDeletion =
                Integer.parseInt(requestDTO.getUserRequest().getUtterance().replaceAll("[^0-9]", ""));

        // 선택한 명령을 취소하기 위해서 명령 아이디를 허브로 보냄
        String resultMsgAboutReservaionDeletion =
                restTemplateService.requestForReservationDeletion(providerId, resIdForDeletion);

        return kakaoSimpleTextService.responserShortMsg(resultMsgAboutReservaionDeletion);
    }


    // 예약 버튼 시나리오s

    // Entry -> Reservation
    @PostMapping("/textbox/end/reservation/entry")
    public ResponseVerTwoDTO textBoxEndFrReservationFrEntry(@RequestBody RequestDTO requestDTO) {
        // 1. 사용자가 선택한 버튼을 저장해야 한다
        //      1-1. 사용자가 선택한 버튼의 버튼 타입을 저장해야 한다
        //      1-2. 사용자가 선택한 버튼의 이벤트 코드를 저장해야 한다
        // 2. timestamp 를 저장해야 한다

        // 5d22a205ffa7480001c5b91d

        log.info("\n\n"); log.info("==================== textBox (End From Reservation From Entry) ====================");
        log.info(requestDTO.toString());

        String blockId = requestDTO.getUserRequest().getBlock().getId();
        String providerId = requestDTO.getUserRequest().getUser().getProperties().getAppUserId();
        Integer btnIdx = Integer.parseInt(requestDTO.getUserRequest().getUtterance().replaceAll("[^0-9]", ""));
        Object dateTimeParams = requestDTO.getAction().getParams().get("datetime");
        Timestamp timeStamp = timeService.convertTimeStampFromObject(dateTimeParams);

        log.info("Block Id -> " + blockId);
        log.info("Button Index -> " + btnIdx);
        log.info("DataTime -> " + dateTimeParams);
        log.info("TimeStamp -> " + timeStamp);

        buildSaveService.initHControlBlocksToNull(providerId);
        buildSaveService.saverSelectedBtn(providerId, btnIdx);
        buildSaveService.initAdditional(providerId);
        buildSaveService.saverTimeStamp(providerId, timeStamp);

        return textBoxResponseService.responserIntervalBox();
    }


    // Entry -> Reservation -> Dynamic
    @PostMapping("/textbox/dy/reservation/entry")
    public ResponseVerTwoDTO textBoxDyFrReservationFrEtnry(@RequestBody RequestDTO requestDTO) {
        // 1. 사용자가 선택한 버튼을 저장해야 한다
        //      1-1. 사용자가 선택한 버튼의 버튼 타입을 저장해야 한다
        //      1-2. 사용자가 선택한 버튼의 이벤트 코드를 저장해야 한다
        // 2. timestamp 를 저장해야 한다

        // 5d3d9028ffa7480001de2c00
        log.info("\n\n"); log.info("==================== textBox (Dy From Reservation From Entry) ====================");
        log.info(requestDTO.toString());

        String providerId = requestDTO.getUserRequest().getUser().getProperties().getAppUserId();
        String blockId = requestDTO.getUserRequest().getBlock().getId();
        Integer btnIdx = Integer.parseInt(requestDTO.getUserRequest().getUtterance().replaceAll("[^0-9]", ""));

        Object dateTimeParams = requestDTO.getAction().getParams().get("datetime");
        Timestamp timeStamp = timeService.convertTimeStampFromObject(dateTimeParams);

        log.info("Block Id -> " + blockId);
        log.info("Button Index -> " + btnIdx);
        log.info("DataTime -> " + dateTimeParams);
        log.info("TimeStamp -> " + timeStamp);

        // 사용자에게 보여줄 Dynamic Box 를 사용자에게 보여줘야 한다.
        return textBoxResponseService.responserDynamicBoxFrTimeFrEntry(providerId, btnIdx, timeStamp);
    }
    // Entry -> Reservation -> Dynamic -> End
    @PostMapping("/textbox/end/dy/reservation/entry")
    public ResponseVerTwoDTO textBoxEndFrDyFrReservationFrEtnry(@RequestBody RequestDTO requestDTO) {
        // 1. 동적 입력 데이터를 Additonal 에 추가해야 한다

        // 5d3d904092690d000124ce0b
        log.info("\n\n"); log.info("==================== textBox (End From Dy From Reservation From Entry) ====================");
        log.info(requestDTO.toString());

        String providerId = requestDTO.getUserRequest().getUser().getProperties().getAppUserId();
        String blockId = requestDTO.getUserRequest().getBlock().getId();
        Long dynamicValue = Long.parseLong(requestDTO.getUserRequest().getUtterance().replaceAll("[^0-9]", ""));

        log.info("Block Id -> " + blockId);
        log.info("INFO >> 사용자가 입력한 dynamicValue -> " + dynamicValue);
        buildSaveService.saverDynamicValue(providerId, dynamicValue);
        return textBoxResponseService.responserIntervalBox();
    }


    // Entry -> Dynamic
    @PostMapping("/textbox/dy/entry")
    public ResponseVerTwoDTO textBoxDyFrEntry(@RequestBody RequestDTO requestDTO) {
        // 1. 사용자가 선택한 버튼을 저장해야 한다
        //      1-1. 사용자가 선택한 버튼의 버튼 타입을 저장해야 한다
        //      1-2. 사용자가 선택한 버튼의 이벤트 코드를 저장해야 한다
        // 2. Cur Box 를 조정해야 한다

        // 5d3d905d92690d000124ce0d
        log.info("\n\n"); log.info("==================== textBox (Dy From Entry) ====================");
        log.info(requestDTO.toString());

        String blockId = requestDTO.getUserRequest().getBlock().getId();
        String providerId = requestDTO.getUserRequest().getUser().getProperties().getAppUserId();
        Integer btnIdx = Integer.parseInt(requestDTO.getUserRequest().getUtterance().replaceAll("[^0-9]", ""));

        log.info("Block Id -> " + blockId);
        log.info("Button Index -> " + btnIdx);

        return textBoxResponseService.responserUDynamicBoxFrEntry(providerId, btnIdx);
    }
    // Entry -> Dynamic -> Reservation -> End
    @PostMapping("/textbox/end/reservation/dy/entry")
    public ResponseVerTwoDTO textBoxEndFrReservationFrDyFrEntry(@RequestBody RequestDTO requestDTO) {
        // 1. timestamp 를 저장해야 한다
        // 2. 동적 입력 데이터를 저장해야 한다

        // 5d3d908fb617ea00018377d4
        log.info("\n\n"); log.info("==================== textBox (End From Reservation From Dy From Entry) ====================");
        log.info(requestDTO.toString());

        String blockId = requestDTO.getUserRequest().getBlock().getId();
        String providerId = requestDTO.getUserRequest().getUser().getProperties().getAppUserId();
        Long dynamicValue = Long.parseLong(requestDTO.getUserRequest().getUtterance().replaceAll("[^0-9]", ""));
        Object dateTimeParams = requestDTO.getAction().getParams().get("datetime");
        Timestamp timeStamp = timeService.convertTimeStampFromObject(dateTimeParams);

        log.info("Block Id -> " + blockId);
        log.info("INFO >> 사용자가 입력한 dynamicValue -> " + dynamicValue);
        log.info("DataTime -> " + dateTimeParams);
        log.info("TimeStamp -> " + timeStamp);

        buildSaveService.initAdditional(providerId);
        buildSaveService.saverTimeStamp(providerId, timeStamp);
        buildSaveService.saverDynamicValue(providerId, dynamicValue);

        return textBoxResponseService.responserIntervalBox();
    }


    // 블록 아이디 : BLOCK_ID_BUILDED_CODES,
    // "전송" 버튼을 클릭하면 이 메소드가 호출
    // 전송하시겠습니까? Yes or No
    @PostMapping("/textbox/select/btn")
    public ResponseVerTwoDTO textBoxSelectBox(@RequestBody RequestDTO requestDto) {

        log.info("============ textBox Select Box With Yes Or No Button 시작 ============");
        log.info("중간에 \"전송\" 버튼을 눌렀을때, 사용자에게 Yes Or No Button 을 보여준다");
        log.info(requestDto.toString());

        return kakaoSimpleTextService.makerTransferSelectCard();
    }


    // 블록 아이디 : BLOCK_ID_TRANSFER_RESULT_DATA,
    // "예" Or "아니오" 버튼을 클릭 -> 아래 메소드 호출
    // "예" -> 허브로 명령 전송
    // "아니오" -> 명령 취소 시나리오 실행
    @PostMapping("/textbox/transfer/data")
    public ResponseVerTwoDTO transferResultData(@RequestBody RequestDTO requestDto) {

        log.info("==================== Transfer Data 시작 ====================");
        log.info("사용자가 선택한 버튼이 \"예\" 버튼 이여서, 빌드된 코드를 전송할 때 호출되는 메소드 입니다.");
        log.info(requestDto.toString());

        String providerId = requestDto.getUserRequest().getUser().getProperties().getAppUserId();
        String utterance = requestDto.getUserRequest().getUtterance();

        if(utterance.equals("명령 전송") ||
                utterance.equals(ONLY_ONE_EXECUTE) || utterance.equals(EVERY_DAY_EXECUTE) || utterance.equals(EVERY_WEEK_EXECUTE)) {

            if(utterance.equals(ONLY_ONE_EXECUTE) || utterance.equals(EVERY_DAY_EXECUTE) || utterance.equals(EVERY_WEEK_EXECUTE)) {
                buildSaveService.saverIntervalSetting(providerId, utterance);
            }
            Build reBuild = buildRepository.find(providerId);
            Path path = reBuild.getPath();

            log.info("INFO >> PATH -> " + path.toString());

            String externalIp = path.getExternalIp();
            int externalPort = path.getExternalPort();
            String hrdwrMacAddr = path.getHrdwrMacAddr();

//            ArrayList<CmdList> btns = reBuild.getCmdList();
  //          CmdList[] arrBtns = btns.toArray(new CmdList[btns.size()]);
            ArrayList<CmdList> cmdLists = reBuild.getCmdList();
            log.info("INFO >> Cmd List 목록 -------");
            for(CmdList tempCmd : cmdLists) {
                log.info("- " + tempCmd);
                if(tempCmd.getAdditional() == null) {
                    tempCmd.setAdditional(new ArrayList<>());
                }
            }

            log.info("Requester Id -> " + reBuild.getHProviderId());

            String url = new String("http://" + externalIp + ":" + externalPort + "/dev/" + hrdwrMacAddr);
            log.info("명령 전송 URL -> " + url);

            // 밑에 임시 주석
            HrdwrControlResult result = restTemplate.postForObject(url,
                    new Object() {
                        public String requester_id = reBuild.getHProviderId();
                        public ArrayList<CmdList> cmdList = cmdLists;
                    },
                    HrdwrControlResult.class);
            //HrdwrControlResult result = new HrdwrControlResult(); result.setStatus(true);

            log.info("INFO >> Result 결과 -> " + result.toString());

            buildRepository.delete(providerId);

            StringBuffer msg = new StringBuffer("");
            if(result.getStatus() == HttpStatus.OK) {
                msg.append("명령 전송이 성공적으로 수행 되었습니다,.\n\n");
            } else {
                msg.append("명령 전송이 실패하였습니다.\n\n");
            }
            msg.append("또 다른 명령을 수행하고 싶으시다면 아래 슬롯을 올려 \"시바\" 버튼을 누르세요.");
            return kakaoSimpleTextService.responserShortMsg(msg.toString());
        }

        // 사용자가 취소 명령을 눌렀다면
        // 취소 선택 버튼 3가지 경우의 수를 리턴한다.secu
        return kakaoSimpleTextService.makerCancleSelectCard(null);
    }


    @PostMapping("/textbox/web")
    public ResponseVerTwoDTO textBoxToWebLink() {
        log.info("\n\n"); log.info("==================== to web link 시작 ====================");
        return kakaoBasicCardService.responserSibaWebLink();
    }


    @PostMapping("/textbox/cancle")
    public ResponseVerTwoDTO textBoxToCancle(@RequestBody RequestDTO requestDto) {
        log.info("\n\n"); log.info("==================== to Cancle 시작 ====================");
        String providerId = requestDto.getUserRequest().getUser().getProperties().getAppUserId();
        Build reBuild = buildRepository.find(providerId);

        // 허브를 선택하지도 않았을때
        if(reBuild == null
        || reBuild.getPath() == null) { // 허브를 선택하지도 않았을때
            return kakaoSimpleTextService.responserShortMsg("취소할 명령이 없습니다.");
        }

        // 허브는 선택했으나, 하드웨어는 선택하지 않았을때, "허브 선택" 만 보여주기
        if(reBuild.getPath().getHrdwrMacAddr() == null) {
            return kakaoSimpleTextService.makerCancleSelectCard('0');
        }

        // 허브와 하드웨어는 선택했으나, 명령을 빌드 중이거나 명령을 빌드한것이 없다면, 모든 버튼 보여주기
        return kakaoSimpleTextService.makerCancleSelectCard(null);
    }


    @PostMapping("/textbox/cancle/all")
    public ResponseVerTwoDTO textBoxCompleteCancle(@RequestBody RequestDTO requestDTO) {
        log.info("\n\n"); log.info("==================== Complete Cancle 시작 ====================");
        log.info(requestDTO.getUserRequest().getBlock() + "");
        String providerId = requestDTO.getUserRequest().getUser().getProperties().getAppUserId();
        buildRepository.delete(providerId);
        return kakaoSimpleTextService.responserShortMsg("모든 명령을 취소하였습니다.");
    }
}