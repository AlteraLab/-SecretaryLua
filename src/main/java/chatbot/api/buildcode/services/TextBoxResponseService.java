package chatbot.api.buildcode.services;

import chatbot.api.buildcode.domain.*;
import chatbot.api.buildcode.domain.response.ResponseHrdwrInfo;
import chatbot.api.buildcode.repository.BuildRepository;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseVerTwoDTO;
import chatbot.api.common.services.KakaoBasicCardService;
import chatbot.api.common.services.KakaoSimpleTextService;
import chatbot.api.mappers.*;
import chatbot.api.skillhub.domain.HubInfoDTO;
import chatbot.api.user.domain.UserInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.discovery.ProviderConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

//@AllArgsConstructor
@Service
@Slf4j
public class TextBoxResponseService {

    private HubMapper hubmapper;

    private UserMapper userMapper;

    private DerivationMapper derivationMapper;

    private HrdwrMapper hrdwrMapper;

    private BoxMapper boxMapper;

    private BtnMapper btnMapper;

    private KakaoBasicCardService kakaoBasicCardService;

    private KakaoSimpleTextService kakaoSimpleTextService;

    private BuildRepository buildRepository;

    private BuildSaveService buildSaveService;

    private RestTemplate restTemplate;


    // 가상 데이터
    private HrdwrDTO[] hrdwrs;

    public TextBoxResponseService(HubMapper hubmapper, UserMapper userMapper, KakaoBasicCardService kakaoBasicCardService, KakaoSimpleTextService kakaoSimpleTextService, BuildRepository buildRepository, BuildSaveService buildSaveService, RestTemplate restTemplate, BoxMapper boxMapper, BtnMapper btnMapper, DerivationMapper derivationMapper, HrdwrMapper hrdwrMapper) {
        this.hubmapper = hubmapper;
        this.userMapper = userMapper;
        this.boxMapper = boxMapper;
        this.btnMapper = btnMapper;
        this.derivationMapper = derivationMapper;
        this.hrdwrMapper = hrdwrMapper;
        this.kakaoBasicCardService = kakaoBasicCardService;
        this.kakaoSimpleTextService = kakaoSimpleTextService;
        this.buildRepository = buildRepository;
        this.buildSaveService = buildSaveService;
        this.restTemplate = restTemplate;

        this.hrdwrs = new HrdwrDTO[2];

        this.hrdwrs[0] = HrdwrDTO.builder()
                .userDefinedName("LG AC01")
                .hrdwrMac("12:12:12:12:12:12")
                .authKey("AAAABBBBCCCCDDDD")
                .build();
        this.hrdwrs[1] = HrdwrDTO.builder()
                .userDefinedName("SAMSUNG AC01")
                .hrdwrMac("72:72:72:72:72:72")
                .authKey("AAAABBBBCCCCDDDD")
                .build();
    }


    public ResponseVerTwoDTO responserHubsBox(String providerId) {

        log.info("================== ResponserHubs ==================");

        if(providerId == null) return kakaoBasicCardService.responserRequestPreSignUp();
        else                   buildSaveService.saverProviderId(providerId);

        UserInfoDto user = userMapper.getUserByProviderId(providerId).get();

        ArrayList<HubInfoDTO> hubs = hubmapper.getUserHubsByUserId(user.getUserId());
        if(hubs == null) {
            return kakaoSimpleTextService.responserShortMsg("사용 가능한 허브가 없습니다.\n허브를 등록해주세요.");
        } else if(hubs.size() == 1) {
            Build reBuild = buildRepository.find(providerId);
            reBuild.setPath(Path.builder()
                    .externalIp(hubs.get(0).getExternalIp())
                    .externalPort(hubs.get(0).getExternalPort())
                    .build());
            reBuild.setHubs(null);

            String url = "http://" + reBuild.getPath().getExternalIp() + ":" + reBuild.getPath().getExternalPort() + "/dev";
            log.info("INFO >> 전달할 URL 주소 : " + url);
            //ResponseHrdwrInfo hrdwrInfo = restTemplate.getForObject(url, ResponseHrdwrInfo.class);
            //log.info("INFO >> RestTemplate 종료");
            // 데이터 받은걸로 가정
            ResponseHrdwrInfo hrdwrInfo = ResponseHrdwrInfo.builder()
                    .hrdwrsInfo(hrdwrs)
                    .status(true)
                    .build();
            log.info("INFO >> DEV INFO 확인 : " + hrdwrInfo.toString());
            if(hrdwrInfo.getDevInfo() == null) {
                buildRepository.delete(providerId);
                return kakaoSimpleTextService.responserShortMsg("허브와 연결된 장비가 없습니다.");
            }
            buildSaveService.saverHrdwrs(providerId, hrdwrInfo.getDevInfo());

            ArrayList<HrdwrDTO> hrdwrs = buildRepository.find(providerId).getHrdwrs();
            return kakaoSimpleTextService.makerHrdwrsCard(hrdwrs);
        } else {
            buildSaveService.saverHubs(providerId, hubs);
            return kakaoSimpleTextService.makerHubsCard(hubs);
        }
    }



    // 사용자가 발화한 허브 번호를 파싱해서 매개변수로 받는다.
    public ResponseVerTwoDTO responserHrdwrsBox(String providerId, int hubSeq) {

        log.info("================== Responser Hrdwrs ==================");

        Build reBuild = buildRepository.find(providerId);

        reBuild.setPath(Path.builder()
                .externalIp(reBuild.getHubs().get(hubSeq - 1).getExplicitIp())
                .externalPort(reBuild.getHubs().get(hubSeq - 1).getExplicitPort())
                .build());
        reBuild.setHubs(null);
        buildRepository.update(reBuild);

        String url = "http://" + reBuild.getPath().getExternalIp() + ":" + reBuild.getPath().getExternalPort() + "/dev";
        log.info("INFO >> 전달할 URL 주소 : " + url);
        //ResponseHrdwrInfo hrdwrInfo = restTemplate.getForObject(url, ResponseHrdwrInfo.class);

        // 데이터 받은걸로 가정
        ResponseHrdwrInfo hrdwrInfo = ResponseHrdwrInfo.builder()
                .hrdwrsInfo(hrdwrs)
                .status(true)
                .build();
        log.info("INFO >> RestTemplate 종료");
        log.info("INFO >> DEV INFO 확인 : " + hrdwrInfo.toString());
        if(hrdwrInfo.getDevInfo() == null) {
            buildRepository.delete(providerId);
            return kakaoSimpleTextService.responserShortMsg("허브와 연결된 장비가 없습니다.");
        }
        buildSaveService.saverHrdwrs(providerId, hrdwrInfo.getDevInfo());

        ArrayList<HrdwrDTO> hrdwrs = buildRepository.find(providerId).getHrdwrs();
        return kakaoSimpleTextService.makerHrdwrsCard(hrdwrs);
    }



    public ResponseVerTwoDTO responserEntryBox(String providerId, int hrdwrSeq) {

        log.info("================== Responser EntryBox 시작 ==================");

        Build reBuild = buildRepository.find(providerId);
        log.info("INFO >> reBuild.getHrdwr -> " + reBuild.getHrdwrs());
        HrdwrDTO selectedHrdwr = reBuild.getHrdwrs().get(hrdwrSeq - 1);
        reBuild.getPath().setHrdwrMacAddr(selectedHrdwr.getHrdwrMac());
        reBuild.setAuthKey(selectedHrdwr.getAuthKey());
        log.info("INFO >> TEST AUTH KEY -> " + reBuild.getAuthKey());
        reBuild.setHrdwrs(null);   // 데이터 참조 해제. 보기 펺나게 할려구
        reBuild.setSelectedBtns(new ArrayList<SelectedBtn>());
        buildRepository.update(reBuild);


        // Entry Box 조회 및 Redis 에 데이터 저장
        log.info("INFO >> boxMapper.getEntryBoxByAuthKey(" + selectedHrdwr.getAuthKey() + ")");
        log.info("INFO >> AUTH KEY ->" + selectedHrdwr.getAuthKey());
        BoxDTO entryBox = boxMapper.getEntryBoxByAuthKey(selectedHrdwr.getAuthKey(), 1L);
        log.info("INFO >> Entry Box -> " + entryBox.toString());
        buildSaveService.saverBox(providerId, entryBox);

        // 버튼 조회 및 Redis 에 데이터 저장
        ArrayList<BtnDTO> btns = btnMapper.getBtnsByBoxIdAndAuthKey(entryBox.getBoxId(), entryBox.getAuthKey());   // 수정 해야함
        buildSaveService.saverBtns(providerId, btns);

        // 파생 조회 및 Redis 에 데이터 저장
        ArrayList<DerivationDTO> derivations = derivationMapper.getDerivationByUpperBoxId(entryBox.getBoxId());
        buildSaveService.saverDerivation(providerId, derivations);

        // 카드 만들기
        return kakaoSimpleTextService.makerBtnsCard(providerId);
    }



    public ResponseVerTwoDTO responserBtnBox(String providerId, int btnSeq) {

        log.info("================== Responser Btn Box 시작 ==================");

        // 실제 버튼 코드를 알아낸다.
        Build reBuild = buildRepository.find(providerId);
        int eventCode = reBuild.getBtns().get(btnSeq - 1).getEventCode();
        log.info("INFO >> 사용자가 선택한 버튼 -> " + reBuild.getBtns().get(btnSeq - 1).toString());
        log.info("INFO >> EventCode -> " + eventCode);

        // 사용자가 선택한 버튼의 이벤트 코드를 redis 에 빌드한다.
        SelectedBtn selectedBtn = SelectedBtn.builder()
                .cmd_code(eventCode)
                .data(0)
                .build();
        log.info("INFO >> 저장할 selectedBtn 정보 -> " + selectedBtn.toString());
        reBuild.getSelectedBtns().add(selectedBtn);
        buildRepository.update(reBuild);

        // curBox + eventCode  ->  lowerBox 를 구한다.
        Long curBoxId = reBuild.getBox().getBoxId();
        Long lowerBoxId = null;
        ArrayList<DerivationDTO> derivations = reBuild.getDerivations();
        DerivationDTO derivation = null;
        for(int i = 0; i < derivations.size(); i++) {
            derivation = derivations.get(i);
            if(eventCode == derivation.getEventCode() && curBoxId == derivation.getUpperBoxId()) {
                lowerBoxId = derivation.getLowerBoxId();
                break;
            }
        }

        // lowerboxid가 없다는 것의 의미는 더이상 하위 텍스트 박스가 존재하지 않는다는 의미.
        if(lowerBoxId == null) {
            return kakaoSimpleTextService.makerTransferSelectCard();
        }

        // lowerBoxId 를 이용하여 Box 조회 및 Redis 에 저장
        BoxDTO box = boxMapper.getBoxByBoxIdAndAuthKey(lowerBoxId, reBuild.getAuthKey());
        buildSaveService.saverBox(providerId, box);

        // 버튼 조회 및 Redis 에 데이터 저장
        ArrayList<BtnDTO> btns = btnMapper.getBtnsByBoxIdAndAuthKey(box.getBoxId(), box.getAuthKey());
        // 버튼을 조회했는데도 null 이란건, 조회할 버튼이 없다는 뜻임. 그러므로 더이상 빌드할 명령이 없다는 의미
        if(btns == null) {
            return kakaoSimpleTextService.makerTransferSelectCard();
        }
        buildSaveService.saverBtns(providerId, btns);

        // 파생 조회 및 Redis 에 데이터 저장
        derivations = derivationMapper.getDerivationByUpperBoxId(box.getBoxId());
        buildSaveService.saverDerivation(providerId, derivations);

        // 카드 만들기
        return kakaoSimpleTextService.makerBtnsCard(providerId);
    }



    public ResponseVerTwoDTO responserBtnBoxFromTime(String providerId, int btnSeq, int minute) {

        log.info("================== Responser Btn Box From Time 시작 ==================");

        // 실제 버튼 코드를 알아낸다.
        Build reBuild = buildRepository.find(providerId);
        int eventCode = reBuild.getBtns().get(btnSeq - 1).getEventCode();
        log.info("INFO >> 사용자가 선택한 버튼 -> " + reBuild.getBtns().get(btnSeq - 1).toString());
        log.info("INFO >> EventCode -> " + eventCode);

        // 사용자가 선택한 버튼의 이벤트 코드를 redis에 빌드한다.
        SelectedBtn selectedBtn = SelectedBtn.builder()
                .cmd_code(eventCode)
                .data(minute)
                .build();
        log.info("INFO >> 저장할 selectedBtn 정보 -> " + selectedBtn.toString());
        reBuild.getSelectedBtns().add(selectedBtn);
        buildRepository.update(reBuild);

        // 아직 시간 박스 전 상태 박스인 상태이다, 그 lower_box가 또 다른 lower_box를 가지고 있는지 확인
        // curBox + eventCode  ->  lowerBox 를 구한다.
        Long curBoxId = reBuild.getBox().getBoxId();
        Long lowerBoxId = null;
        ArrayList<DerivationDTO> derivations = reBuild.getDerivations();
        DerivationDTO derivation = null;
        for(int i = 0; i < derivations.size(); i++) {
            derivation = derivations.get(i);
            if(eventCode == derivation.getEventCode() && curBoxId == derivation.getUpperBoxId()) {
                lowerBoxId = derivation.getLowerBoxId();
                break;
            }
        }

        // 구한 Lower_box_id 를 이용해서 db에서 timboxId 의 lower_box_id를 찾아내야 한다 ㅅㅍㅅㅍ.
        // 그럼 그게 진짜 lowerboxid
        // 만약 그렇게 구한 lower_box_id가 null 이면 더이상 하위 트리가 없는거임.
        lowerBoxId = derivationMapper.getLowerBoxIdFromUpperTimeBoxId(lowerBoxId);
        if(lowerBoxId == null) {
            return kakaoSimpleTextService.makerTransferSelectCard();
        }

        // lowerBoxId 를 이용하여 Box 조회 및 Redis 에 저장
        BoxDTO box = boxMapper.getBoxByBoxIdAndAuthKey(lowerBoxId, reBuild.getAuthKey());
        buildSaveService.saverBox(providerId, box);

        // 버튼 조회 및 Redis 에 데이터 저장
        ArrayList<BtnDTO> btns = btnMapper.getBtnsByBoxIdAndAuthKey(box.getBoxId(), box.getAuthKey());
        // 버튼을 조회했는데도 null 이란건, 조회할 버튼이 없다는 뜻임. 그러므로 더이상 빌드할 명령이 없다는 의미
        if(btns == null) {
            return kakaoSimpleTextService.makerTransferSelectCard();
        }
        buildSaveService.saverBtns(providerId, btns);

        // 파생 조회 및 Redis 에 데이터 저장
        derivations = derivationMapper.getDerivationByUpperBoxId(box.getBoxId());
        buildSaveService.saverDerivation(providerId, derivations);

        return kakaoSimpleTextService.makerBtnsCard(providerId);
    }



    public ResponseVerTwoDTO responserInputBox(String providerId, int btnSeq) {

        log.info("================== Responser Input Box 시작 ==================");

        // 실제 버튼 코드를 알아낸다.
        Build reBuild = buildRepository.find(providerId);
        int eventCode = reBuild.getBtns().get(btnSeq - 1).getEventCode();
        log.info("INFO >> 사용자가 선택한 버튼 -> " + reBuild.getBtns().get(btnSeq - 1).toString());
        log.info("INFO >> EventCode -> " + eventCode);

        // 사용자가 선택한 버튼의 이벤트 코드를 Redis에 빌드.
        // Input 버튼 이므로, 버튼 코드만 빌드한 상태임. 뒤에 발생하는 추가 입력도 빌드 해줘야 한다.
        SelectedBtn selectedBtn = SelectedBtn.builder()
                .cmd_code(eventCode)
                .data(0)
                .build();
        log.info("INFO >> 저장할 selectedBtn 정보 -> " + selectedBtn.toString());
        reBuild.getSelectedBtns().add(selectedBtn);
        buildRepository.update(reBuild);

        // curBox + eventCode -> LowerBox 를 구한다.
        Long curBoxId = reBuild.getBox().getBoxId();
        Long lowerBoxId = null;
        ArrayList<DerivationDTO> derivations = reBuild.getDerivations();
        DerivationDTO derivation = null;
        for(int i = 0; i < derivations.size(); i++) {
            derivation = derivations.get(i);
            if(eventCode == derivation.getEventCode() && curBoxId == derivation.getUpperBoxId()) {
                lowerBoxId = derivation.getLowerBoxId();
                break;
            }
        }

        // lowerBoxId 를 이용하여 Box 조회 및 Redis 에 저장
        BoxDTO box = boxMapper.getBoxByBoxIdAndAuthKey(lowerBoxId, reBuild.getAuthKey());
        buildSaveService.saverBox(providerId, box);

        // 파생 조회 및 Redis 에 데이터 저장
        derivations = derivationMapper.getDerivationByUpperBoxId(box.getBoxId());
        buildSaveService.saverDerivation(providerId, derivations);

        // 카드 만들기
        return kakaoSimpleTextService.makerInputCard(providerId);
    }



    public ResponseVerTwoDTO responserBtnBoxFromInputContext(String providerId, int inputValue) {

        log.info("==================== Responser BtnBox From Input Context 시작 ====================");

        // Input 값 최종 빌딩
        Build reBuild = buildRepository.find(providerId);
        reBuild.getSelectedBtns().get(reBuild.getSelectedBtns().size() - 1).setData(inputValue);
        log.info("INFO >> 최종적으로 빌딩할 Input Btn   -> " + reBuild.getSelectedBtns().get(reBuild.getSelectedBtns().size() - 1).getCmd_code());
        log.info("INFO >> 최종적으로 빌딩할 Input Value -> " + reBuild.getSelectedBtns().get(reBuild.getSelectedBtns().size() - 1).getData());
        buildRepository.update(reBuild);

        // 하위 박스 아이디를 구한다.
        // Input 텍스트 박스에서는 파생 레코드가 1개 밖에 없기 때문에 아래와 같이 코드를 작성
        DerivationDTO derivation = reBuild.getDerivations().get(0);
        Long lowerBoxId = derivation.getLowerBoxId();
        if(lowerBoxId == null) {
            kakaoSimpleTextService.makerTransferSelectCard();
        }

        // lowerBoxId 를 이용하여 Box 조회 및 Redis 에 저장
        BoxDTO box = boxMapper.getBoxByBoxIdAndAuthKey(lowerBoxId, reBuild.getAuthKey());
        buildSaveService.saverBox(providerId, box);

        // 버튼 조회 및 Redis 에 데이터 저장
        ArrayList<BtnDTO> btns = btnMapper.getBtnsByBoxIdAndAuthKey(box.getBoxId(), box.getAuthKey());
        // 버튼을 조회했는데도 null 이란건, 조회할 버튼이 없다는 뜻임. 그러므로 더이상 빌드할 명령이 없다는 의미
        if(btns == null) {
            return kakaoSimpleTextService.makerTransferSelectCard();
        }
        buildSaveService.saverBtns(providerId, btns);

        // 파생 조회 및 Redis 에 데이터 저장
        ArrayList<DerivationDTO> derivations = derivationMapper.getDerivationByUpperBoxId(box.getBoxId());
        buildSaveService.saverDerivation(providerId, derivations);

        return kakaoSimpleTextService.makerBtnsCard(providerId);
    }
}
