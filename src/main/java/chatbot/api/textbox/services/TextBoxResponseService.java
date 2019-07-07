package chatbot.api.textbox.services;

import chatbot.api.textbox.domain.*;
import chatbot.api.textbox.domain.path.Path;
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

    private KakaoBasicCardService kakaoBasicCardService;

    private KakaoSimpleTextService kakaoSimpleTextService;

    private BuildRepository buildRepository;

    private BuildSaveService buildSaveService;

    private BuildActionService buildActionService;

    private BuildAllocaterService buildAllocaterService;



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

            if (buildActionService.actionRequestAndSaverAboutHrdwr(providerId)) {
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

        if (buildActionService.actionRequestAndSaverAboutHrdwr(providerId)) {
            return kakaoSimpleTextService.responserShortMsg("허브와 연결된 장비가 없습니다.");
        }
        return kakaoSimpleTextService.makerHrdwrsCard(providerId);
    }


    public ResponseVerTwoDTO responserEntryBox(String providerId, Integer hrdwrSeq) {

        log.info("================== Responser EntryBox 시작 ==================");
        buildSaveService.saverSelectedHrdwr(providerId, hrdwrSeq);// 선택된 하드웨어 저장
        buildSaveService.saverPathAboutMac(providerId);           // 하드웨어 맥주소 저장
        buildSaveService.saverMultipleData(providerId);           // 데이터를 빌드하는데 필요한 boxs, btns, derivations 저장
        buildSaveService.initCmdListWhenEntry(providerId);        // When Create Entry Box, cmdList 초기화(객체 할당)
        buildSaveService.initCurBoxWhenEntry(providerId);         // When Create Entry Box, 엔트리 박스(현재 박스) 구하기
        buildSaveService.initCurBtns(providerId);                 // 현재 박스 안에 존재하는 버튼들 초기화
        //buildSaveService.initJustBeforeIsEntryToTrue(providerId); // isE ntry 값을 true로 초기화
        return kakaoSimpleTextService.makerEntryCard(providerId); // 카드 만들기
    }


    // when before box type is entry, excute
    public ResponseVerTwoDTO responserAnyBox(String providerId, Integer btnIdx) {
        Character btnType = buildAllocaterService.allocateBtnTypeAccrdToBtnIdx(providerId, btnIdx);
        ResponseVerTwoDTO responseVerTwoDTO = buildAllocaterService.allocateAnyBoxAccrdToBtnType(providerId, btnType);
        return responseVerTwoDTO;
    }
}
