package chatbot.api.textbox.services;

import chatbot.api.textbox.domain.*;
import chatbot.api.textbox.domain.path.HrdwrDTO;
import chatbot.api.textbox.domain.path.Path;
import chatbot.api.textbox.domain.textboxdata.DerivationDTO;
import chatbot.api.textbox.domain.transfer.cmdList;
import chatbot.api.textbox.repository.BuildRepository;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseVerTwoDTO;
import chatbot.api.common.services.KakaoBasicCardService;
import chatbot.api.common.services.KakaoSimpleTextService;
import chatbot.api.mappers.*;
import chatbot.api.skillhub.domain.HubInfoDTO;
import chatbot.api.user.domain.UserInfoDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@AllArgsConstructor
@Service
@Slf4j
public class TextBoxResponseService {

    private HubMapper hubmapper;

    private UserMapper userMapper;

    private DerivationMapper derivationMapper;

    private BoxMapper boxMapper;

    private KakaoBasicCardService kakaoBasicCardService;

    private KakaoSimpleTextService kakaoSimpleTextService;

    private BuildRepository buildRepository;

    private BuildSaveService buildSaveService;

    private BuildActionService buildActionService;


    public ResponseVerTwoDTO responserHubsBox(String providerId) {

        log.info("================== ResponserHubs ==================");

        if (providerId == null) return kakaoBasicCardService.responserRequestPreSignUp();
        else buildSaveService.saverProviderId(providerId);

        UserInfoDto user = userMapper.getUserByProviderId(providerId).get();

        ArrayList<HubInfoDTO> hubs = hubmapper.getUserHubsByUserId(user.getUserId());
        buildSaveService.saverHubs(providerId, hubs);

        if (hubs == null) {
            return kakaoSimpleTextService.responserShortMsg("사용 가능한 허브가 없습니다.\n허브를 등록해주세요.");
        } else if (hubs.size() == 1) {
            buildSaveService.saverPathAboutAddr(
                    providerId,
                    Path.builder()
                            .externalIp(hubs.get(0).getExternalIp())
                            .externalPort(hubs.get(0).getExternalPort())
                            .build());

            if (buildActionService.actionRequestAndSaverHrdwr(providerId)) {
                return kakaoSimpleTextService.responserShortMsg("허브와 연결된 장비가 없습니다.");
            }
            return kakaoSimpleTextService.makerHrdwrsCard(providerId);
        } else {
            return kakaoSimpleTextService.makerHubsCard(hubs);
        }
    }


    // 사용자가 발화한 허브 번호를 파싱해서 매개변수로 받는다.
    public ResponseVerTwoDTO responserHrdwrsBox(String providerId, int hubSeq) {

        log.info("================== Responser Hrdwrs ==================");
        Build reBuild = buildRepository.find(providerId);
        buildSaveService.saverPathAboutAddr(
                providerId,
                Path.builder()
                        .externalIp(reBuild.getHubs().get(hubSeq - 1).getExplicitIp())
                        .externalPort(reBuild.getHubs().get(hubSeq - 1).getExplicitPort())
                        .build());

        if (buildActionService.actionRequestAndSaverHrdwr(providerId)) {
            return kakaoSimpleTextService.responserShortMsg("허브와 연결된 장비가 없습니다.");
        }
        return kakaoSimpleTextService.makerHrdwrsCard(providerId);
    }


    public ResponseVerTwoDTO responserEntryBox(String providerId, int hrdwrSeq) {

        log.info("================== Responser EntryBox 시작 ==================");

        Build reBuild = buildRepository.find(providerId);
        log.info("INFO >> reBuild.getHrdwr -> " + reBuild.getHrdwrs());
        HrdwrDTO selectedHrdwr = reBuild.getHrdwrs().get(hrdwrSeq - 1);
        reBuild.getPath().setHrdwrMacAddr(selectedHrdwr.getHrdwrMac());
        reBuild.setHrdwrs(null);   // 데이터 참조 해제. 보기 펺나게 할려구
        reBuild.setCmdLists(new ArrayList<cmdList>());
        buildRepository.update(reBuild);


        // Entry Box 조회 및 Redis 에 데이터 저장
        log.info("INFO >> boxMapper.getEntryBoxByAuthKey(" + selectedHrdwr.getAuthKey() + ")");
        log.info("INFO >> AUTH KEY ->" + selectedHrdwr.getAuthKey());

        // 버튼 조회 및 Redis 에 데이터 저장

        // 파생 조회 및 Redis 에 데이터 저장

        // 카드 만들기
        return kakaoSimpleTextService.makerBtnsCard(providerId);
    }


    public ResponseVerTwoDTO responserBtnBox(String providerId, int btnSeq) {

        log.info("================== Responser Btn Box 시작 ==================");

        // 실제 버튼 코드를 알아낸다.
        Build reBuild = buildRepository.find(providerId);
        buildRepository.update(reBuild);

        // curBox + eventCode  ->  lowerBox 를 구한다.
        Long lowerBoxId = null;
        ArrayList<DerivationDTO> derivations = reBuild.getDerivations();
        DerivationDTO derivation = null;
        for (int i = 0; i < derivations.size(); i++) {
            derivation = derivations.get(i);
        }

        // lowerboxid가 없다는 것의 의미는 더이상 하위 텍스트 박스가 존재하지 않는다는 의미.
        if (lowerBoxId == null) {
            return kakaoSimpleTextService.makerTransferSelectCard();
        }

        // lowerBoxId 를 이용하여 Box 조회 및 Redis 에 저장


        // 파생 조회 및 Redis 에 데이터 저장
        buildSaveService.saverDerivation(providerId, derivations);

        // 카드 만들기
        return kakaoSimpleTextService.makerBtnsCard(providerId);
    }


    public ResponseVerTwoDTO responserBtnBoxFromTime(String providerId, int btnSeq, int minute) {

        log.info("================== Responser Btn Box From Time 시작 ==================");

        // 실제 버튼 코드를 알아낸다.

        return kakaoSimpleTextService.makerBtnsCard(providerId);
    }


    public ResponseVerTwoDTO responserInputBox(String providerId, int btnSeq) {

        log.info("================== Responser Input Box 시작 ==================");

        // 실제 버튼 코드를 알아낸다.
        Build reBuild = buildRepository.find(providerId);

        // curBox + eventCode -> LowerBox 를 구한다.
        Long lowerBoxId = null;
        ArrayList<DerivationDTO> derivations = reBuild.getDerivations();
        DerivationDTO derivation = null;
        return null;
    }




    public ResponseVerTwoDTO responserBtnBoxFromInputContext(String providerId, int inputValue) {

        log.info("==================== Responser BtnBox From Input Context 시작 ====================");

        // Input 값 최종 빌딩
        Build reBuild = buildRepository.find(providerId);
        buildRepository.update(reBuild);

        // 하위 박스 아이디를 구한다.
        // Input 텍스트 박스에서는 파생 레코드가 1개 밖에 없기 때문에 아래와 같이 코드를 작성
        DerivationDTO derivation = reBuild.getDerivations().get(0);

        // lowerBoxId 를 이용하여 Box 조회 및 Redis 에 저장



        // 파생 조회 및 Redis 에 데이터 저장
        return kakaoSimpleTextService.makerBtnsCard(providerId);
    }
}
