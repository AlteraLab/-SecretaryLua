package chatbot.api.textbox.services;

import chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseVerTwoDTO;
import chatbot.api.common.services.KakaoSimpleTextService;
import chatbot.api.textbox.domain.Build;
import chatbot.api.textbox.domain.blockid.BelowBlockIds;
import chatbot.api.textbox.domain.textboxdata.BoxDTO;
import chatbot.api.textbox.domain.textboxdata.BtnDTO;
import chatbot.api.textbox.domain.textboxdata.DerivationDTO;
import chatbot.api.textbox.repository.BuildRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

import static chatbot.api.textbox.utils.TextBoxConstants.*;

@Service
@Slf4j
public class BuildAllocaterService {

    @Autowired
    private BuildRepository buildRepository;

    @Autowired
    private KakaoSimpleTextService kakaoSimpleTextService;

    @Autowired
    private BuildCheckerService buildCheckerService;

    @Autowired
    private BuildSaveService buildSaveService;

    @Autowired
    private JudgeService judgeService;


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



    // boxId 로 box 를 구하는 메소드
    public BoxDTO allocateBoxByBoxId(String providerId, Integer boxId) {
        log.info("=========== allocate box by boxid 시작 ===========");
        Build reBuild = buildRepository.find(providerId);
        ArrayList<BoxDTO> boxs = reBuild.getBoxs();
        BoxDTO returnBox = null;
        for(BoxDTO tempBox : boxs) {
            if(tempBox.getBoxId() == boxId) {
                log.info("allocate box -> " + tempBox.toString());
                log.info("=========== allocate box by boxid 종료 ===========");
                returnBox = tempBox;
                break;
            }
        }
        return returnBox;
    }


    // 버튼 타입이 제어일때, 해당 버튼의 하위 박스 타입들에 따라서 hControlBlocks (blockId들) 할당
    public void allocateHControlBlocksBycBoxTypeWhenControlType(String providerId, BtnDTO curBtn) {
        log.info("=========== allocate HControl BlockIDs By lower box type When btnType is Control Type 시작 ===========");
        Build reBuild = buildRepository.find(providerId);
        HashMap<Integer, BelowBlockIds> hControlBlockIds = reBuild.getHControlBlocks();

        // End 혹은 Control Box Type이 나올때까지 depth 를 구한다.
        // depth는 entry box로 부터 하위 박스(end 혹은 제어도 포함)가 하나 있을때마다 +1 시킨다
        HashMap<Integer, Integer> hBoxTypeOfDepth = new HashMap<Integer, Integer>(); // <depth, boxType>
        ArrayList<DerivationDTO> derivations = reBuild.getDerivations();
        BoxDTO curBox = reBuild.getCurBox();
        Integer depth = 0;
        Integer lowerBoxId = null;
        Integer boxId = null;
        BoxDTO lowerBox = null;

        while (depth < MAX_DEPTH) {
            for(DerivationDTO tempDerivation : derivations) {
                if(depth == 0) { // depth 가 0 일 때만, curBtn 의 버튼 코드와 derivation 을 이용해서, lowerBoxId 를 구한다
                    if(curBox.getBoxId() == tempDerivation.getUpperBoxId() &&
                            curBtn.getBtnCode() == tempDerivation.getBtnCode()) {
                        lowerBoxId = tempDerivation.getLowerBoxId();
                        break;
                    }
                } else if(depth != 0){ // depth 가 0 이 아닐때, derivation 과 boxId 를 이용해서 lowerBoxId 를 구한다
                    if(tempDerivation.getUpperBoxId() == boxId) {
                        lowerBoxId = tempDerivation.getLowerBoxId();
                        break;
                    }
                }
            }
            depth++; //end에 속할 수도 있으니 그냥 무조건 depth + 1 시킴
            if(lowerBoxId == null) { // lowerBoxId 가 null 이면, 하위 박스가 없는 거임, 즉 end 박스란 거임.
                break;
            } else {
                boxId = lowerBoxId;
            }
            // boxId 를 이용해서 박스를 얻은 후 -> 박스 타입을 체크한다. control이면 end 랑 같으니까, break 검
            lowerBox = this.allocateBoxByBoxId(providerId, boxId);
            if(lowerBox.getBoxType() == BOX_TYPE_CONTROL) {   // 만약 하위 박스의 박스 타입이 Control 이였다면,
                hBoxTypeOfDepth.put(depth, BOX_TYPE_CONTROL);
                break;
            } else if(lowerBox.getBoxType() == BOX_TYPE_TIME) {
                hBoxTypeOfDepth.put(depth, BOX_TYPE_TIME);
            } else if(lowerBox.getBoxType() == BOX_TYPE_DYNAMIC) {
                hBoxTypeOfDepth.put(depth, BOX_TYPE_DYNAMIC);
            }
            // init lowerBoxId
            lowerBoxId = null;
        }

        // 박스 타입 할당
        BelowBlockIds belowBlockIds = new BelowBlockIds();
        Integer tempOneBelowBoxType = null;
        if(depth == 1) {  // 1. (제어) 시나리오
            // depth == 1, 이란 것은 단순히 버튼 제어라는 의미임. additional도 없고, 추가 텍스트 박스도 없는 경우임.
            belowBlockIds.setBlockIdOnebelow(BLOCK_ID_END_ENTRY);
            belowBlockIds.setBlockIdTwobelow(null);
        } else if(depth == 2) {
            tempOneBelowBoxType = hBoxTypeOfDepth.get(1);
            if(tempOneBelowBoxType == BOX_TYPE_TIME) {  // 2. (제어->시간) 시나리오
                belowBlockIds.setBlockIdOnebelow(BLOCK_ID_END_TIME_ENTRY);
            } else if(tempOneBelowBoxType == BOX_TYPE_DYNAMIC) {  // 3. (제어->동적) 시나리오
                belowBlockIds.setBlockIdOnebelow(BLOCK_ID_DYNAMIC_ENTRY);
                belowBlockIds.setBlockIdTwobelow(BLOCK_ID_END_DYNAMIC_ENTRY);
            }
        } else if(depth == 3) {
            tempOneBelowBoxType = hBoxTypeOfDepth.get(1);
            if(tempOneBelowBoxType == BOX_TYPE_TIME) {  // 4. (제어->시간->동적) 시나리오
                belowBlockIds.setBlockIdOnebelow(BLOCK_ID_DYNAMIC_TIME_ENTRY);
                belowBlockIds.setBlockIdTwobelow(BLOCK_ID_END_DYNAMIC_TIME_ENTRY);
            } else if(tempOneBelowBoxType == BOX_TYPE_DYNAMIC) {  // 5. (제어->동적->시간) 시나리오
                belowBlockIds.setBlockIdOnebelow(BLOCK_ID__DYNAMIC_ENTRY);
                belowBlockIds.setBlockIdTwobelow(BLOCK_ID_END_TIME__DYNAMIC_ENTRY);
            }
        }

        hControlBlockIds.put(curBtn.getIdx(), belowBlockIds);
        log.info("hControlBlocks.Idx -> " + curBtn.getIdx());
        log.info("hControlBlocks.belowBlockIds -> " + hControlBlockIds.get(curBtn.getIdx()));
        buildRepository.update(reBuild);
        log.info("=========== allocate HControl BlockIDs By lower box type When btnType is Control Type 종료 ===========");
    }



    public void allocateHControlBlocksBycBoxTypeWhenReservationType(String providerId, BtnDTO curBtn) {
        log.info("=========== allocate HControl BlockIDs By lower box type When btnType is Reservation Type 시작 ===========");
        Build reBuild = buildRepository.find(providerId);
        HashMap<Integer, BelowBlockIds> hControlBlockIds = reBuild.getHControlBlocks();

        // 1. 첫 번째 박스 타입이 무엇이냐??
        // 2. 딥스가 2 ? 3 ?
        ArrayList<DerivationDTO> derivations = reBuild.getDerivations();
        BoxDTO curBox = reBuild.getCurBox();
        Integer depth = 0;

        BoxDTO lowerBox = null;
        Integer lowerBoxId = null;
        Integer boxId = null;
        Integer firstBoxType = null;

        while(depth < MAX_DEPTH) {
            for(DerivationDTO tempDerivation : derivations) {
                if (depth == 0){
                    if(curBox.getBoxId() == tempDerivation.getUpperBoxId() &&
                            curBtn.getBtnCode() == tempDerivation.getBtnCode()) {
                        lowerBoxId = tempDerivation.getLowerBoxId();
                        break;
                    }
                } else if(depth != 0) {
                    if(tempDerivation.getUpperBoxId() == boxId) {
                        lowerBoxId = tempDerivation.getLowerBoxId();
                        break;
                    }
                }
            }

            depth++;

            log.info(depth + " :: LowerBox Id -> " + lowerBoxId);

            if(lowerBoxId == null) {
                log.info("Break :: " + depth + " Lower Box Id == NULL");
                break;
            } else {
                boxId = lowerBoxId;
            }

            lowerBox = this.allocateBoxByBoxId(providerId, boxId);

            if(depth == 1) {
                log.info("First Box -> " + lowerBox);
                firstBoxType = lowerBox.getBoxType();
            }

            lowerBoxId = null;
        }

        // 블록 아이디 할당
        BelowBlockIds belowBlockIds = new BelowBlockIds();
        log.info("Depth -> " + depth);
        if(depth == 2) {
            belowBlockIds.setBlockIdOnebelow(BLOCK_ID_END_RESERVATION_ENTRY);
        } else if (depth == 3){
            if(firstBoxType == BOX_TYPE_TIME) {
                belowBlockIds.setBlockIdOnebelow(BLOCK_ID_DY_RESERVATION_ENTRY);
            } else if(firstBoxType == BOX_TYPE_DYNAMIC) {
                belowBlockIds.setBlockIdOnebelow(BLOCK_ID_DY_ENTRY);
            }
        }

        hControlBlockIds.put(curBtn.getIdx(), belowBlockIds);
        log.info("hControlBlocks.Idx -> " + curBtn.getIdx());
        log.info("hControlBlocks.belowBlockIds -> " + hControlBlockIds.get(curBtn.getIdx()));
        buildRepository.update(reBuild);
        log.info("=========== allocate HControl BlockIDs By lower box type When btnType is Reservation Type 종료 ===========");
    }


    // 버튼 타입이 제어가 아닐때, 해당 버튼의 타입에 따라서 blockId 할당
    public String allocateBlockIdByBtnTypeWhenNotControlAndReservation(BtnDTO curBtn) {
        log.info("=========== allocate BlockId By Current Button Type When btnType is Not Control Type And Not Reservation Type 시작 ===========");
        String returnBlockId = null;
        if(curBtn.getBtnType() == BUTTON_TYPE_LOOKUP_RESERVATION) {
            returnBlockId = BLOCK_ID_TO_LOOKUP_RESERVATION;
            log.info("Block Id -> BLOCK_ID_TO_LOOKUP_RESERVATION");
        } else if(curBtn.getBtnType() == BUTTON_TYPE_LOOKUP_SENSING) {
            returnBlockId = BLOCK_ID_TO_LOOKUP_SENSING;
            log.info("Block Id -> BLOCK_ID_TO_LOOKUP_SENSING");
        } else if(curBtn.getBtnType() == BUTTON_TYPE_LOOKUP_DEVICE) {
            returnBlockId = BLOCK_ID_TO_LOOKUP_DEVICE;
            log.info("Block Id -> BLOCK_ID_TO_LOOKUP_DEVICE");
        }
        log.info("=========== allocate BlockId By Current Button Type When btnType is Not Control Type And Not Reservation Type 종료 ===========");
        return returnBlockId;
    }


    // 하위 박스가 있다면 Control Box, 하위 박스가 없다면 End Box 를 보여줘야한다
    public ResponseVerTwoDTO allocaterResponseVerTwoDtoByExistLowerBox(String providerId) {
        log.info("=========== allocate ResponseVerTwoDto By Exist Lower Box 시작 ===========");

        Boolean isJudgeBox = true;

        while(isJudgeBox) {
            isJudgeBox = judgeService.execute(providerId);
        }

        log.info("=========== allocate ResponseVerTwoDto By Exist Lower Box 종료 ===========");

        return judgeService.executeByCurBoxType(providerId);

// 이게 필요한 코드인지 나중에 고민해보기..., 지우지는 말기 중요한 코드인 것만은 확실함
        /*
        if(buildCheckerService.existLowerBox(providerId)) { // 하위 박스가 존재한다면(Control Type Box 라면...)
            // 현재 박스는 위에서 이미 초기화 되어있는 상태임
            // 1. HControlBlocks 에 객체를 새로 할당
            // 2. 제어 박스 내에 현재 버튼 목록을 초기화
            // 3. selectedBtn 초기화
            buildSaveService.initHControlBlocks(providerId);    // 1
            buildSaveService.initCurBtns(providerId);           // 2 + 여기서 버튼들의 인덱스 값도 증가 시킴
            buildSaveService.initSelectedBtnToNull(providerId); // 3
            responseVerTwoDTO = kakaoSimpleTextService.makerEntryAndControlCard(providerId);
        }
        else { // 하위 박스가 존재하지 않는다면
            responseVerTwoDTO = kakaoSimpleTextService.makerTransferSelectCard();
        }
        log.info("=========== allocate ResponseVerTwoDto By Exist Lower Box 종료 ===========");
        return responseVerTwoDTO;
        */
    }
}