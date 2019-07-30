package chatbot.api.common.services;

import chatbot.api.textbox.domain.*;
import chatbot.api.textbox.domain.path.HrdwrDTO;
import chatbot.api.textbox.domain.reservation.ReservationDTO;
import chatbot.api.textbox.domain.reservation.ReservationListDTO;
import chatbot.api.textbox.domain.textboxdata.BoxDTO;
import chatbot.api.textbox.domain.textboxdata.BtnDTO;
import chatbot.api.textbox.domain.textboxdata.DerivationDTO;
import chatbot.api.textbox.repository.BuildRepository;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.QuickReply;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseVerTwoDTO;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.component.simpleText.ComponentSimpleText;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.component.simpleText.SimpleText;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.SkillTemplate;
import chatbot.api.skillhub.domain.HubInfoDTO;
import chatbot.api.textbox.services.BuildAllocaterService;
import chatbot.api.textbox.services.BuildCheckerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static chatbot.api.textbox.utils.TextBoxConstants.*;

@Service
@Slf4j
public class KakaoSimpleTextService {

    @Autowired
    private BuildRepository buildRepository;

    @Autowired
    private BuildAllocaterService buildAllocaterService;

    @Autowired
    private BuildCheckerService buildCheckerService;





    public ResponseVerTwoDTO responserShortMsg(String msg) {

        SimpleText simpleTextVo = new SimpleText();
        simpleTextVo.setText(msg);

        ComponentSimpleText simpleText = new ComponentSimpleText();
        simpleText.setSimpleText(simpleTextVo);

        ArrayList<Object> outputs = new ArrayList<>();
        outputs.add(simpleText);

        SkillTemplate template = new SkillTemplate();
        template.setOutputs(outputs);

        return new ResponseVerTwoDTO().builder()
                .version("2.0")
                .template(template)
                .build();
    }



    public ResponseVerTwoDTO makerTransferBtnText() {

        String msg = "하위 명령이 없습니다. \n\n버튼을 누르면 명령을 전송합니다.";
        SimpleText simpleTextVo = new SimpleText();
        simpleTextVo.setText(msg);

        ComponentSimpleText simpleText = new ComponentSimpleText();
        simpleText.setSimpleText(simpleTextVo);

        ArrayList<Object> outputs = new ArrayList<>();
        outputs.add(simpleText);

        SkillTemplate template = new SkillTemplate();
        template.setOutputs(outputs);

        ArrayList<QuickReply> quickReplies = new ArrayList<QuickReply>();

        QuickReply transferBtn = QuickReply.builder()
                .label("전송")
                .messageText("명령 전송")
                .action("block")
                .blockId(BLOCK_ID_SELECT_BOX_YES_OR_NO)
                .build();

        quickReplies.add(transferBtn);
        template.setQuickReplies(quickReplies);

        return new ResponseVerTwoDTO().builder()
                .version("2.0")
                .template(template)
                .build();
    }



    public ResponseVerTwoDTO makerTransferSelectCard() {

        String msg = "명령을 전송하시겠습니까?";
        SimpleText simpleTextVo = new SimpleText();
        simpleTextVo.setText(msg);

        ComponentSimpleText simpleText = new ComponentSimpleText();
        simpleText.setSimpleText(simpleTextVo);

        ArrayList<Object> outputs = new ArrayList<>();
        outputs.add(simpleText);

        SkillTemplate template = new SkillTemplate();
        template.setOutputs(outputs);

        ArrayList<QuickReply> quickReplies = new ArrayList<QuickReply>();

        QuickReply transferBtn = QuickReply.builder()
                .label("예")
                .messageText("명령 전송")
                .action("block")
                .blockId(BLOCK_ID_TRANSFER_RESULT_DATA)
                .build();

        QuickReply cancleBtn = QuickReply.builder()
                .label("아니오")
                .messageText("명령 취소")
                .action("block")
                .blockId(BLOCK_ID_TRANSFER_RESULT_DATA)
                .build();

        quickReplies.add(transferBtn);
        quickReplies.add(cancleBtn);

        template.setQuickReplies(quickReplies);

        return new ResponseVerTwoDTO().builder()
                .version("2.0")
                .template(template)
                .build();
    }


    public ResponseVerTwoDTO makerCancleSelectCard() {

        StringBuffer msg = new StringBuffer("명령을 취소하였습니다.\n\n");

        msg.append("1. 허브 재선택\n");
        msg.append("2. 명령 재선택\n");
        msg.append("3. 취소\n\n");

        msg.append("1. 허브 재선택\n모든 명령을 취소하고 사용하고자 하는 허브까지 모두 다시 선택합니다.\n\n");
        msg.append("2. 명령 재선택\n선택한 허브와 모듈은 그대로 입니다. 전송하고자 하는 명령만 다시 선택합니다.\n\n");
        msg.append("3. 취소\n허브, 모듈, 명령에 대한 내용 모두 취소합니다.\n\n");

        msg.append("선택해주세요.\n");


        SimpleText simpleTextVo = new SimpleText();
        simpleTextVo.setText(msg.toString());

        ComponentSimpleText simpleText = new ComponentSimpleText();
        simpleText.setSimpleText(simpleTextVo);

        ArrayList<Object> outputs = new ArrayList<>();
        outputs.add(simpleText);

        SkillTemplate template = new SkillTemplate();
        template.setOutputs(outputs);

        ArrayList<QuickReply> quickReplies = new ArrayList<QuickReply>();

        QuickReply cancleBtnToControlHubs = QuickReply.builder()
                .label("1")
                .messageText("시바")
                .action("message")
                .build();

        QuickReply cancleBtnToCodeList = QuickReply.builder()
                .label("2")
                .messageText("명령 재선택")
                .action("block")
                .blockId(BLOCK_ID_TO_ANY_BOX)
                .build();

   /*     QuickReply cancleBtn = QuickReply.builder()
                .label("3")
                .messageText("취소")
                .action("block")
                .blockId(BLOCK_ID_CANCLE_COMPLETE)
                .build();*/

        quickReplies.add(cancleBtnToControlHubs);
        quickReplies.add(cancleBtnToCodeList);
        //quickReplies.add(cancleBtn);

        template.setQuickReplies(quickReplies);

        return new ResponseVerTwoDTO().builder()
                .version("2.0")
                .template(template)
                .build();
    }



    public ResponseVerTwoDTO makerCancleCompleteCard() {

        String msg = "모든 명령을 취소하였습니다.\n\n또 다른 명령을 내리실려면 슬롯을 올려 \"시바\" 버튼을 클릭하세요.";
        SimpleText simpleTextVo = new SimpleText();
        simpleTextVo.setText(msg);

        ComponentSimpleText simpleText = new ComponentSimpleText();
        simpleText.setSimpleText(simpleTextVo);

        ArrayList<Object> outputs = new ArrayList<>();
        outputs.add(simpleText);

        SkillTemplate template = new SkillTemplate();
        template.setOutputs(outputs);

        return new ResponseVerTwoDTO().builder()
                .version("2.0")
                .template(template)
                .build();
    }



    // 사용 가능한 허브가 한 개 이상인 경우 허브 리스트 카드를 만들고 반환
    public ResponseVerTwoDTO makerHubsCard(ArrayList<HubInfoDTO> hubs) {

        log.info("================== makerHubsCard 시작 ==================");

        SimpleText text = new SimpleText();
        StringBuffer responseMsg = new StringBuffer("사용할 수 있는 허브 목록 입니다.\n\n");

        for (int i = 0; i < hubs.size(); i++) {
            responseMsg.append((i + 1) + ". " + hubs.get(i).getHubName() + "\n");
        }
        responseMsg.append("\n버튼을 클릭해주세요.");
        text.setText(responseMsg.toString());

        ComponentSimpleText simpleText = new ComponentSimpleText();
        simpleText.setSimpleText(text);

        ArrayList<Object> outputs = new ArrayList<Object>();
        outputs.add(simpleText);

        // "버튼" 꾸미기
        ArrayList<QuickReply> quickReplies = new ArrayList<QuickReply>();
        for (int i = 0; i < hubs.size(); i++) {
            QuickReply quick = QuickReply.builder()
                    .label(Integer.toString(i + 1))
                    .action("block")
                    .messageText("허브 " + (i + 1) + "번")
                    .blockId(BLOCK_ID_TO_HRDWRS_BOX)
                    .build();
                quickReplies.add(quick);
        }

        SkillTemplate template = new SkillTemplate(outputs, quickReplies);

        log.info("================== makerHubsCard 종료 ==================");

        return new ResponseVerTwoDTO().builder()
                .version("2.0")
                .template(template)
                .build();
    }



    public ResponseVerTwoDTO makerHrdwrsCard(String providerId) {

        log.info("================== makerHrdwrsCard 시작 ==================");
        ArrayList<HrdwrDTO> hrdwrs = buildRepository.find(providerId).getHrdwrs();

        SimpleText text = new SimpleText();
        StringBuffer responseMsg = new StringBuffer("사용할 수 있는 하드웨어 목록 입니다.\n\n");
        for (int i = 0; i < hrdwrs.size(); i++) {
            responseMsg.append((i + 1) + ". " + hrdwrs.get(i).getUserDefinedName() + "\n");
        }
        responseMsg.append("\n하드웨어를 선택해주세요.");
        text.setText(responseMsg.toString());

        ComponentSimpleText simpleText = new ComponentSimpleText();
        simpleText.setSimpleText(text);

        ArrayList<Object> outputs = new ArrayList<Object>();
        outputs.add(simpleText);

        // 2. 버튼 꾸미기
        ArrayList<QuickReply> quickReplies = new ArrayList<QuickReply>();
        for (int i = 0; i < hrdwrs.size(); i++) {
            QuickReply quick = QuickReply.builder()
                    .label(Integer.toString(i + 1))
                    .messageText("하드웨어 " + (i + 1) + "번")
                    //.messageText("모듈 " + (i + 1) + "번")
                    .action("block")
                    .blockId(BLOCK_ID_TO_ENTRY_BOX)
                    .build();
             quickReplies.add(quick);
        }

        SkillTemplate template = new SkillTemplate(outputs, quickReplies);

        log.info("================== makerHrdwrsCard 종료    ==================");

        return new ResponseVerTwoDTO().builder()
                .version("2.0")
                .template(template)
                .build();
    }


    public ResponseVerTwoDTO makerEntryAndControlCard(String providerId) {

        log.info("================== maker Entry Card 시작 ==================");

        Build reBuild = buildRepository.find(providerId);

        // 1. 사용자에게 보여줄 텍스트
        SimpleText text = new SimpleText();
        StringBuffer responseMsg = new StringBuffer(reBuild.getCurBox().getPreText() + "\n\n");
        for (int i = 0; i < reBuild.getCurBtns().size(); i++) {
            responseMsg.append((reBuild.getCurBtns().get(i).getIdx())
                    + ". " + reBuild.getCurBtns().get(i).getBtnName() + "\n");
        }
        responseMsg.append("\n" + reBuild.getCurBox().getPostText());
        text.setText(responseMsg.toString());

        ComponentSimpleText simpleText = new ComponentSimpleText();
        simpleText.setSimpleText(text);

        ArrayList<Object> outputs = new ArrayList<Object>();
        outputs.add(simpleText);


        // 2. 버튼 만들기
        // 적어도 entry box 에서는 하나의 버튼이 갖는 blockId 값을 할당하기 위해서
        // 텍스트 박스 타입이 아닌 현재 텍스트 박스내의 각 버튼의 타입으로 할당해줘야 한다.
        // 그렇지만 만약 버튼 타입이 '1'(제어)인 경우에는 하위 박스들을 조회하여 5가지 하위 시나리오에 맞춰서 blockId를 할당 해줘야 한다.
        ArrayList<QuickReply> quickReplies = new ArrayList<QuickReply>();
        String blockId = null;
        BtnDTO curBtnOfCurBtns = null;
        for (int idx = 1; idx < reBuild.getCurBtns().size() + 1; idx++) {
            // Entry Box에 들어갈 버튼들의 블록 아이디를 정해줘야 한다.
            // 버튼 타입만 알면 제어 / 조회 / 예약 시나리오를 알 수 있다.
            // 만약 제어 시나리오라면 하위 텍스트 박스들의 타입들을 이용해서 시나리오를 구성해줘야 한다.

            // 먼저 제어 버튼인지 아닌지 조회한다. 현재 내가 가진것중에 인덱스 값으로 curBtns 에서 해당 btn을 찾아내고
            // 이 버튼의 타입이 제어인지 체크
            curBtnOfCurBtns = reBuild.getCurBtns().get(idx - 1);
            log.info("현재 버튼 정보 -> " + curBtnOfCurBtns);
            if(buildCheckerService.isControlTypeOf(curBtnOfCurBtns)) {  // 제어 버튼 이므로 하위 박스의 타입에 따라 blockId 할당
                buildAllocaterService.allocateHControlBlocksBycBoxTypeWhenControlType(providerId, curBtnOfCurBtns);
                reBuild = buildRepository.find(providerId);
                log.info("IDX (" + idx + ") -> " + reBuild.getHControlBlocks().get(idx));
                log.info(reBuild.getHControlBlocks().toString());
                blockId = reBuild.getHControlBlocks().get(idx).getBlockIdOnebelow();
            } else if(buildCheckerService.isReservationTypeOf(curBtnOfCurBtns)) {
                buildAllocaterService.allocateHControlBlocksBycBoxTypeWhenReservationType(providerId, curBtnOfCurBtns);
                reBuild = buildRepository.find(providerId);
                log.info("IDX (" + idx + ") -> " + reBuild.getHControlBlocks().get(idx));
                log.info(reBuild.getHControlBlocks().toString());
                blockId = reBuild.getHControlBlocks().get(idx).getBlockIdOnebelow();
            } else { // 버튼의 타입에 따라서 어떤 센싱인지 혹은 예약 인지에 따라 blockid 할당
                blockId = buildAllocaterService.allocateBlockIdByBtnTypeWhenNotControl(curBtnOfCurBtns);
            }
            log.info("Block Id -> " + blockId);
            QuickReply quick = QuickReply.builder()
                    .label(Integer.toString(reBuild.getCurBtns().get(idx - 1).getIdx()))
                    .messageText("버튼 " + reBuild.getCurBtns().get(idx - 1).getIdx() + "번")
                    .action("block")
                    .blockId(blockId)
                    .build();
            quickReplies.add(quick);
        }

        QuickReply transferBtn = QuickReply.builder()
                .label("전송")
                .messageText("명령 전송")
                .action("block")
                .blockId(BLOCK_ID_SELECT_BOX_YES_OR_NO)
                .build();
        quickReplies.add(transferBtn);
        SkillTemplate template = new SkillTemplate(outputs, quickReplies);

        log.info("================== maker Entry Card 종료 ==================");
        return new ResponseVerTwoDTO().builder()
                .version("2.0")
                .template(template)
                .build();
    }


    public ResponseVerTwoDTO makerReservationListCard(ReservationListDTO reservationList) {
        log.info("================== maker Reservation List Card 시작    ==================");
        StringBuffer msg = new StringBuffer("예약된 명령들 입니다.(" + reservationList.getReservationDTOList().size() + "개)\n\n");
        for(int i = 0; i < reservationList.getReservationDTOList().size(); i++) {
            msg.append((i + 1) + ". " +
                    "시간설정(" + reservationList.getReservationDTOList().get(i).getActionAt() + ")\n"); // 추후 수정
        }
        msg.append("\n예약 취소를 원하신다면 선택하세요.");


        SimpleText simpleTextVo = new SimpleText();
        simpleTextVo.setText(msg.toString());

        ComponentSimpleText simpleText = new ComponentSimpleText();
        simpleText.setSimpleText(simpleTextVo);

        ArrayList<Object> outputs = new ArrayList<>();
        outputs.add(simpleText);

        ArrayList<QuickReply> quickReplies = new ArrayList<QuickReply>();
        for(int i = 0; i < reservationList.getReservationDTOList().size(); i++) {
            QuickReply quick = QuickReply.builder()
                    .label(Integer.toString(i + 1))
                    .messageText("취소할 예약 아이디 : " +
                            reservationList.getReservationDTOList().get(i).getReservationId())
                    .action("block")
                    .blockId(BLOCK_ID_RESERVATION_DELETION_RESULT)
                    .build();
            quickReplies.add(quick);
        }

        SkillTemplate template = new SkillTemplate(outputs, quickReplies);

        log.info("================== maker Reservation List Card 종료    ==================");
        return new ResponseVerTwoDTO().builder()
                .version("2.0")
                .template(template)
                .build();
    }
        
    
    // 사용자에게 보여줄 동적 카드 When Dynamic From Time From Entry
    public ResponseVerTwoDTO makerDynamicCardWhenDynamicFrTimeFrEntry(String providerId) {

        log.info("================== maker Dynamic Card When Dynamic From Time From Entry 시작    ==================");
        Build reBuild = buildRepository.find(providerId);

        ArrayList<DerivationDTO> derivations = reBuild.getDerivations();
        BoxDTO curBox = reBuild.getCurBox();
        BtnDTO selectedBtn = reBuild.getSelectedBtn();

        for(DerivationDTO tempDerivation : derivations) {
            if(curBox.getBoxId() == tempDerivation.getUpperBoxId() &&
            selectedBtn.getBtnCode() == tempDerivation.getBtnCode()) {
                curBox = buildAllocaterService.allocateBoxByBoxId(providerId, tempDerivation.getLowerBoxId());
                break;
            }
        }
        for(DerivationDTO tempDerivation : derivations) {
            if(curBox.getBoxId() == tempDerivation.getUpperBoxId()) {
                curBox = buildAllocaterService.allocateBoxByBoxId(providerId, tempDerivation.getLowerBoxId());
                break;
            }
        }
        reBuild.setCurBox(curBox);
        buildRepository.update(reBuild);


        // 카드 만들기
        BoxDTO dynamicBox = curBox;
        log.info("Dynamic Box -> " + dynamicBox);
        log.info("================== maker Dynamic Card When Dynamic From Time From Entry 종료    ==================");
        return new ResponseVerTwoDTO().builder()
                .version("2.0")
                .template(this.makerTemplateWhenMakingDynamicCard(dynamicBox))
                .build();
    }


    // 사용자에게 보여줄 동적 카드 When _Dynamic From Entry
    public ResponseVerTwoDTO makerDynamicCardWhenUDynamicFrEntry(String providerId) {
        log.info("================== maker Dynamic Card When _Dynamic From Entry 시작    ==================");
        Build reBuild = buildRepository.find(providerId);

        ArrayList<DerivationDTO> derivations = reBuild.getDerivations();
        BoxDTO curBox = reBuild.getCurBox();
        BtnDTO selectedBtn = reBuild.getSelectedBtn();

        for(DerivationDTO tempDerivation : derivations) {
            if(curBox.getBoxId() == tempDerivation.getUpperBoxId() &&
                    selectedBtn.getBtnCode() == tempDerivation.getBtnCode()) {
                curBox = buildAllocaterService.allocateBoxByBoxId(providerId, tempDerivation.getLowerBoxId());
                break;
            }
        }
        reBuild.setCurBox(curBox);
        buildRepository.update(reBuild);

        // 카드 만들기
        BoxDTO dynamicBox = curBox;
        log.info("Dynamic Box -> " + dynamicBox);

        String msg = dynamicBox.getPreText() + "\n\n" + dynamicBox.getPostText();

        SimpleText simpleTextVo = new SimpleText();
        simpleTextVo.setText(msg);

        ComponentSimpleText simpleText = new ComponentSimpleText();
        simpleText.setSimpleText(simpleTextVo);

        ArrayList<Object> outputs = new ArrayList<>();
        outputs.add(simpleText);

        SkillTemplate template = new SkillTemplate();
        template.setOutputs(outputs);
        log.info("================== maker Dynamic Card When _Dynamic From Entry 종료    ==================");
        return new ResponseVerTwoDTO().builder()
                .version("2.0")
                .template(template)
                .build();
    }


    // 사용자에게 보여줄 동적 카드 When Dynamic From Entry
    public ResponseVerTwoDTO makerDynamicCardWhenDynamicFrEntry(String providerId) {
        log.info("================== maker Dynamic Card When Dynamic From Entry 종료    ==================");
        Build reBuild = buildRepository.find(providerId);

        ArrayList<DerivationDTO> derivations = reBuild.getDerivations();
        BoxDTO curBox = reBuild.getCurBox();
        BtnDTO selectedBtn = reBuild.getSelectedBtn();

        for(DerivationDTO tempDerivation : derivations) {
            if(curBox.getBoxId() == tempDerivation.getUpperBoxId() &&
                    selectedBtn.getBtnCode() == tempDerivation.getBtnCode()) {
                curBox = buildAllocaterService.allocateBoxByBoxId(providerId, tempDerivation.getLowerBoxId());
                break;
            }
        }
        reBuild.setCurBox(curBox);
        buildRepository.update(reBuild);


        // 카드 만들기
        BoxDTO dynamicBox = curBox;
        log.info("Dynamic Box -> " + dynamicBox);
        log.info("================== maker Dynamic Card When Dynamic From Entry 종료    ==================");
        return new ResponseVerTwoDTO().builder()
                .version("2.0")
                .template(this.makerTemplateWhenMakingDynamicCard(dynamicBox))
                .build();
    }


    public SkillTemplate makerTemplateWhenMakingDynamicCard(BoxDTO curBox) {
        log.info("================== maker Template When Making Dynamic Card 시작    ==================");

        String msg = curBox.getPreText() + "\n\n" + curBox.getPostText();

        SimpleText simpleTextVo = new SimpleText();
        simpleTextVo.setText(msg);

        ComponentSimpleText simpleText = new ComponentSimpleText();
        simpleText.setSimpleText(simpleTextVo);

        ArrayList<Object> outputs = new ArrayList<>();
        outputs.add(simpleText);

        SkillTemplate template = new SkillTemplate();
        template.setOutputs(outputs);

        log.info("================== maker Template When Making Dynamic Card 종료    ==================");
        return template;
    }


    public ResponseVerTwoDTO makerIntervalCard() {
        log.info("================== maker Interval Card 시작    ==================");
        String msg = "명령 실행 주기를 설정해주세요.\n\n" +
                "1. 1회\n" +
                "2. 매일\n" +
                "3. 매주\n\n" +
                "버튼을 선택해주세요.";

        SimpleText simpleTextVo = new SimpleText();
        simpleTextVo.setText(msg);

        ComponentSimpleText simpleText = new ComponentSimpleText();
        simpleText.setSimpleText(simpleTextVo);

        ArrayList<Object> outputs = new ArrayList<>();
        outputs.add(simpleText);

        SkillTemplate template = new SkillTemplate();
        template.setOutputs(outputs);

        ArrayList<QuickReply> quickReplies = new ArrayList<QuickReply>();

        QuickReply onlyOne = QuickReply.builder()
                .label("1")
                .messageText(ONLY_ONE_EXECUTE)
                .action("block")
                .blockId(BLOCK_ID_TRANSFER_RESULT_DATA)
                .build();

        QuickReply everyDay = QuickReply.builder()
                .label("2")
                .messageText(EVERY_DAY_EXECUTE)
                .action("block")
                .blockId(BLOCK_ID_TRANSFER_RESULT_DATA)
                .build();

        QuickReply everyWeek = QuickReply.builder()
                .label("3")
                .messageText(EVERY_WEEK_EXECUTE)
                .action("block")
                .blockId(BLOCK_ID_TRANSFER_RESULT_DATA)
                .build();

        quickReplies.add(onlyOne);
        quickReplies.add(everyDay);
        quickReplies.add(everyWeek);

        template.setQuickReplies(quickReplies);

        log.info("================== maker Interval Card 종료    ==================");
        return new ResponseVerTwoDTO().builder()
                .version("2.0")
                .template(template)
                .build();
    }
}


/*
    public ResponseVerTwoDTO makerBtnsCard(String providerId) {

        log.info("================== makerBtnsCard 시작 ==================");

        Build reBuild = buildRepository.find(providerId);

        TextBoxDTO curBox = reBuild.getBox();
        ArrayList<BtnDTO> btns = reBuild.getBtns();

        // 카드 만들기
        SimpleText text = new SimpleText();
        StringBuffer responseMsg = new StringBuffer(curBox.getPreText() + "\n\n");
        for (int i = 0; i < btns.size(); i++) {
            responseMsg.append((i + 1) + ". " + btns.get(i).getBtnName() + "\n");
        }
        responseMsg.append("\n" + curBox.getPostText());
        text.setText(responseMsg.toString());

        ComponentSimpleText simpleText = new ComponentSimpleText();
        simpleText.setSimpleText(text);

        ArrayList<Object> outputs = new ArrayList<Object>();
        outputs.add(simpleText);

        // 버튼 꾸미기
        ArrayList<QuickReply> quickReplies = new ArrayList<QuickReply>();
        for (int i = 0; i < btns.size(); i++) {
            QuickReply quick = QuickReply.builder()
                    .label(Integer.toString(i + 1))
                    .messageText("버튼 " + (i + 1) + "번")
                    .action("block")
                    .blockId(btns.get(i).getBlockId())
                    .build();

            quickReplies.add(quick);
        }

        QuickReply transferBtn = QuickReply.builder()
                .label("전송")
                .messageText("명령 전송")
                .action("block")
                .blockId(BLOCK_ID_BUILDED_CODES)
                .build();

        quickReplies.add(transferBtn);

        SkillTemplate template = new SkillTemplate(outputs, quickReplies);

        log.info("================== makerBtnsCard 종료    ==================");

        return new ResponseVerTwoDTO().builder()
                .version("2.0")
                .template(template)
                .build();
       return null;
    }*/