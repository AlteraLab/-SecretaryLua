package chatbot.api.common.services;

import chatbot.api.buildcode.domain.*;
import chatbot.api.buildcode.repository.BuildRepository;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.QuickReply;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseVerTwoDTO;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.component.simpleText.ComponentSimpleText;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.component.simpleText.SimpleText;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.SkillTemplate;
import chatbot.api.skillhub.domain.HubInfoDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static chatbot.api.buildcode.utils.BuildConstants.*;

@Service
@AllArgsConstructor
@Slf4j
public class KakaoSimpleTextService {

    private BuildRepository buildRepository;


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
                .blockId(BLOCK_ID_BUILDED_CODES)
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
                .blockId(BLOCK_ID_TRANSFER_RESULT)
                .build();

        QuickReply cancleBtn = QuickReply.builder()
                .label("아니오")
                .messageText("명령 취소")
                .action("block")
                .blockId(BLOCK_ID_TRANSFER_RESULT)
                .build();

        quickReplies.add(transferBtn);
        quickReplies.add(cancleBtn);

        template.setQuickReplies(quickReplies);

        return new ResponseVerTwoDTO().builder()
                .version("2.0")
                .template(template)
                .build();
    }



    public ResponseVerTwoDTO makerTransferCompleteCard(String msg) {

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
                .blockId(BLOCK_ID_BTN_TEXTBOX)
                .build();

        QuickReply cancleBtn = QuickReply.builder()
                .label("3")
                .messageText("취소")
                .action("block")
                .blockId(BLOCK_ID_CANCLE_COMPLETE)
                .build();

        quickReplies.add(cancleBtnToControlHubs);
        quickReplies.add(cancleBtnToCodeList);
        quickReplies.add(cancleBtn);

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
                    .blockId(BLOCK_ID_RETURN_HRDWRS)
                    .build();
                quickReplies.add(quick);
        }

        SkillTemplate template = new SkillTemplate(outputs, quickReplies);

        log.info("================== makerHubsCard 끝 ==================");

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
                    .blockId(BLOCK_ID_RETURN_BTNS)
                    .build();
             quickReplies.add(quick);
        }

        SkillTemplate template = new SkillTemplate(outputs, quickReplies);

        log.info("================== makerHrdwrsCard 끝    ==================");

        return new ResponseVerTwoDTO().builder()
                .version("2.0")
                .template(template)
                .build();
    }



    public ResponseVerTwoDTO makerBtnsCard(String providerId) {

        log.info("================== makerBtnsCard 시작 ==================");

        Build reBuild = buildRepository.find(providerId);

        BoxDTO curBox = reBuild.getBox();
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

        log.info("================== makerBtnsCard 끝    ==================");

        return new ResponseVerTwoDTO().builder()
                .version("2.0")
                .template(template)
                .build();
    }



    public ResponseVerTwoDTO makerInputCard(String providerId) {

        log.info("================== makerInputCard 시작    ==================");


        Build reBuild = buildRepository.find(providerId);
        BoxDTO inputBox = reBuild.getBox();
        log.info("INFO >> Box_Type == 3 -> Input Box 의 타입 : " + inputBox.getBoxType());

        String msg = inputBox.getPreText() + "\n\n" + inputBox.getPostText();

        SimpleText simpleTextVo = new SimpleText();
        simpleTextVo.setText(msg);

        ComponentSimpleText simpleText = new ComponentSimpleText();
        simpleText.setSimpleText(simpleTextVo);

        ArrayList<Object> outputs = new ArrayList<>();
        outputs.add(simpleText);

        SkillTemplate template = new SkillTemplate();
        template.setOutputs(outputs);

        log.info("================== makerInputCard 끝    ==================");

        return new ResponseVerTwoDTO().builder()
                .version("2.0")
                .template(template)
                .build();
    }
}
