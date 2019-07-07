package chatbot.api.textbox.services;

import chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseVerTwoDTO;
import chatbot.api.textbox.domain.Build;
import chatbot.api.textbox.domain.textboxdata.BoxDTO;
import chatbot.api.textbox.domain.textboxdata.BtnDTO;
import chatbot.api.textbox.domain.textboxdata.DerivationDTO;
import chatbot.api.textbox.repository.BuildRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static chatbot.api.textbox.utils.TextBoxConstants.*;

@Service
@Slf4j
@AllArgsConstructor
public class BuildAllocaterService {

    private BuildRepository buildRepository;



    public Integer allocateLowerBoxType(String providerId, Integer idx) {
        log.info("=========== allocate LowerBoxType 시작 ===========");

        Build reBuild = buildRepository.find(providerId);

        // 현재 박스 아이디 구하기
        Integer curBoxId = reBuild.getCurBox().getBoxId();

        // 선택될 수도 있는 버튼의 버튼코드 구하기
        BtnDTO curBtn = reBuild.getCurBtns().get(idx);
        Integer curBtnCode = curBtn.getBtnCode();

        // (현재 박스 + 선택될 수도 있는 버튼의 버튼 코드) -> 현재 derivation 구하기
        ArrayList<DerivationDTO> derivations = reBuild.getDerivations();
        DerivationDTO curDerivation = null;
        for(DerivationDTO temp : derivations) {
            if(temp.getUpperBoxId() == curBoxId && temp.getBtnCode() == curBtnCode) {
                curDerivation = temp;
                break;
            }
        }

        // curDerivation으로 하위 박스 아이디 구하기
        Integer lowerBoxId = null;
        Integer lowerBoxType = null;
        if(curDerivation == null) {  // 다음으로 넘어갈 derivation이 없다면?
            return lowerBoxId = BOX_TYPE_END;
        } else {
            lowerBoxId = curDerivation.getLowerBoxId();
            log.info("Lower Box Id -> " + curDerivation.toString());
            log.info("Lower Box Id -> " + curDerivation.getLowerBoxId());
        }

        // 하위 박스 아이디를 이용해서 하위 박스 타입 구하기
        ArrayList<BoxDTO> boxs = reBuild.getBoxs();
        for(BoxDTO tempBox : boxs) {
            if(tempBox.getBoxId() == lowerBoxId) {
                lowerBoxType = tempBox.getBoxType();
                break;
            }
        }

        log.info("Lower Box Id -> " + lowerBoxType);
        log.info("=========== allocate LowerBoxType 종료 ===========");
        return lowerBoxType;
    }


    public String allocateBlockId(Integer lowerBoxType) {
        log.info("=========== allocateBlockId 시작 ===========");
        String blockId = null;
        if(lowerBoxType == BOX_TYPE_BUTTON) {
            blockId = BLOCK_ID_BTN_TEXTBOX;
        } else if (lowerBoxType == BOX_TYPE_TIME) {
            blockId = BLOCK_ID_TIME_TEXTBOX;
        } else if (lowerBoxType == BOX_TYPE_DYNAMIC) {
            blockId = BLOCK_ID_INPUT_TEXTBOX;
        } else if (lowerBoxType == BOX_TYPE_END){
            blockId = BLOCK_ID_BUILDED_CODES;
        }
        log.info("Block Id -> " + blockId);
        log.info("=========== allocateBlockId 종료 ===========");
        return blockId;
    }


    public Character allocateBtnTypeAccrdToBtnIdx(String providerId, Integer btnIdx) {
        log.info("========= Allocate Button Type 시작 =========");
        Build reBuild = buildRepository.find(providerId);
        ArrayList<BtnDTO> curBtns = reBuild.getCurBtns();
        BtnDTO selectedBtn = null;
        for(BtnDTO tempCurBtn : curBtns) {
            if(btnIdx == tempCurBtn.getIdx()) {
                selectedBtn = tempCurBtn;
                break;
            }
        }
        log.info("Selected Button -> " + selectedBtn);
        log.info("Selected Button Type -> " + selectedBtn.getBtnType());
        log.info("========= Allocate Button Type 종료 =========");
        return selectedBtn.getBtnType();
    }

    public ResponseVerTwoDTO allocateAnyBoxAccrdToBtnType(String providerId, Character btnType) {
        log.info("========= Allocate Any Box 시작 =========");
        ResponseVerTwoDTO responseVerTwoDTO = null;
        if(btnType == BUTTON_TYPE_CONTROL) {

            log.info("=== (제어) 시나리오 ===");
            responseVerTwoDTO = null;

        } else if(btnType == BUTTON_TYPE_LOOKUP_RESERVATION) {

            log.info("=== (조회-허브-예약) 시나리오 ===");
            responseVerTwoDTO = null;

        } else if(btnType == BUTTON_TYPE_LOOKUP_SENSING) {

            log.info("=== (조회-허브-센싱) 시나리오 ===");
            responseVerTwoDTO = null;

        } else if(btnType == BUTTON_TYPE_LOOKUP_DEVICE) {

            log.info("=== (조회-디바이스) 시나리오 ===");
            responseVerTwoDTO = null;

        }
        log.info("========= Allocate Any Box 종료 =========");
        return responseVerTwoDTO;
    }
}