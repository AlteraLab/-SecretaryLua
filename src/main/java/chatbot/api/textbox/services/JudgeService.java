package chatbot.api.textbox.services;

import chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseVerTwoDTO;
import chatbot.api.common.services.KakaoSimpleTextService;
import chatbot.api.common.services.RestTemplateService;
import chatbot.api.textbox.domain.Build;
import chatbot.api.textbox.domain.textboxdata.BoxDTO;
import chatbot.api.textbox.domain.textboxdata.BtnDTO;
import chatbot.api.textbox.domain.textboxdata.DerivationDTO;
import chatbot.api.textbox.domain.transfer.Additional;
import chatbot.api.textbox.repository.BuildRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import static chatbot.api.textbox.utils.TextBoxConstants.BOX_TYPE_JUDGE;
import static chatbot.api.textbox.utils.TextBoxConstants.BOX_TYPE_JUDGE_END;

@Service
@Slf4j
public class JudgeService {

    @Autowired
    private BuildRepository buildRepository;

    @Autowired
    private RestTemplateService restTemplateService;

    @Autowired
    private BuildAllocaterService buildAllocaterService;

    @Autowired
    private BuildSaveService buildSaveService;

    @Autowired
    private KakaoSimpleTextService kakaoSimpleTextService;

    @Autowired
    private RestTemplate restTemplate;


    public Boolean execute(String providerId) {

        Build reBuild = buildRepository.find(providerId);

        // 0. 만약 curBox 가 null 이라면 return false, (각 시나리오의 끝에 Judge가 없는 경우)
        if(reBuild.getCurBox() == null) {
            return false;
        }

        // 1. 문자열 치환
        String preText = this.ProcessStringForJudge(providerId);

        // 2. 허브로 데이터 전송 및 응답
        Integer judgeStatus = restTemplateService.requestJudgeStatus(providerId, preText);

        // 3. 현재 박스 아이디 + JudgfeStatus 를 이용해서 버튼을 찾음
        BtnDTO curBtn = null;
        for(BtnDTO tempBtn : reBuild.getBtns()) {
            if(tempBtn.getBoxId() == reBuild.getCurBox().getBoxId()
            && tempBtn.getIdx() ==  judgeStatus) {
                curBtn = tempBtn;
            }
        }

        // 4. derivation, boxId, btnCode 를 이용해서 하위 박스를 찾는다
        ArrayList<DerivationDTO> derivations = reBuild.getDerivations();
        BoxDTO lowerBox = null;
        for(DerivationDTO tempDerivation : derivations) {
            if(tempDerivation.getUpperBoxId() == reBuild.getCurBox().getBoxId()
            && tempDerivation.getBtnCode() == curBtn.getBtnCode()) {
                lowerBox = buildAllocaterService.allocateBoxByBoxId(providerId, tempDerivation.getLowerBoxId());
            }
        }

        // 5. 하위 박스 업데이트
        reBuild.setCurBox(lowerBox);
        buildRepository.update(reBuild);

        // 6. 하위 박스가 Judge Box 인 경우 -> true 리턴
        if(lowerBox != null && lowerBox.getBoxType() == BOX_TYPE_JUDGE) {
            return true;
        }
        // 하위 박스가 없거나, JudgeBox 가 아니라면, false 리턴
        return false;
    }


    public ResponseVerTwoDTO executeByCurBoxType(String providerId) {

        log.info("Judge Service - executeByCurBoxType 시작");

        Build reBuild = buildRepository.find(providerId);
        BoxDTO lowerBox = reBuild.getCurBox();
        ResponseVerTwoDTO responseVerTwoDTO = null;

        if(lowerBox == null) {
            // 하위 박스가 없는 경우 -> cmdList 를 추가로 만들 필요 없이, cmdList 를 허브에게 보냄
            responseVerTwoDTO = kakaoSimpleTextService.makerTransferSelectCard();
        } else if(lowerBox.getBoxType() == BOX_TYPE_JUDGE_END) {
            // 하위 박스 타입이 End (type 8) 박스인 경우 -> End Box 를 단순히 표출하고 끝
            responseVerTwoDTO = kakaoSimpleTextService.responserShortMsg(lowerBox.getPreText());
            buildRepository.delete(providerId);
        } else {
            // 하위 박스가 있는데 End 박스가 아닌 경우 -> 명령을 계속 빌딩
            buildSaveService.initHControlBlocks(providerId);    // 1
            buildSaveService.initCurBtns(providerId);           // 2 + 여기서 버튼들의 인덱스 값도 증가 시킴
            buildSaveService.initSelectedBtnToNull(providerId); // 3
            responseVerTwoDTO = kakaoSimpleTextService.makerEntryAndControlCard(providerId);
        }

        log.info("Judge Service - executeByCurBoxType 종료");

        return responseVerTwoDTO;
    }


    public String ProcessStringForJudge(String providerId) {
        Build reBuild = buildRepository.find(providerId);

        // 1. 문자열 관련
        String textOfCurBox = reBuild.getCurBox().getPreText();
        ArrayList<Additional> additionals = reBuild.getCmdList().get(reBuild.getCmdList().size() - 1).getAdditional();

        if(textOfCurBox.contains("#{dynamic}")) {
            for(Additional tempAdditional : additionals) {
                if(tempAdditional.getType().equals('2')) {
                    textOfCurBox = textOfCurBox.replace("#{dynamic}", tempAdditional.getValue().toString()); // 문자열 치환
                    break;
                }
            }
        }
        if(textOfCurBox.contains("#{time}")) {
            for(Additional tempAdditional : additionals) {
                if(tempAdditional.getType().equals('1')) {
                    textOfCurBox = textOfCurBox.replace("#{time}", tempAdditional.getValue().toString()); // 문자열 치환
                    break;
                }
            }
        }
        if(textOfCurBox.contains("$")) {
            textOfCurBox = textOfCurBox.replaceAll("[$]", "");  // 문자열 제거
        }
        return textOfCurBox;
    }
}
