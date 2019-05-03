package chatbot.api.common.services;

import chatbot.api.common.domain.kakao.openbuilder.responseVer2.QuickReply;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseDtoVerTwo;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseJsonFormat.Component.simpleText.ComponentSimpleText;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseJsonFormat.Component.simpleText.SimpleText;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.SkillTemplate;
import chatbot.api.order.domain.CmdOrder;
import chatbot.api.order.domain.DevOrder;
import chatbot.api.order.domain.MainOrder;
import chatbot.api.order.repository.MainOrderRepository;
import chatbot.api.order.repository.MainOrderRepositoryImpl;
import chatbot.api.skillHub.domain.HubInfoDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static chatbot.api.order.utils.OrderConstans.*;

@Service
@AllArgsConstructor
@Slf4j
public class KakaoSimpleTextService {

    private MainOrderRepositoryImpl mainOrderRepository;


    public ResponseDtoVerTwo responserShortMsg(String msg) {

        SimpleText simpleTextVo = new SimpleText();
        simpleTextVo.setText(msg);

        ComponentSimpleText simpleText = new ComponentSimpleText();
        simpleText.setSimpleText(simpleTextVo);

        ArrayList<Object> outputs = new ArrayList<>();
        outputs.add(simpleText);

        SkillTemplate template = new SkillTemplate();
        template.setOutputs(outputs);

        return new ResponseDtoVerTwo().builder()
                .version("2.0")
                .template(template)
                .build();
    }



    public ResponseDtoVerTwo makerDirectInputText(String providerId, int parentCode) {

        MainOrder reMainOrder = mainOrderRepository.find(providerId);
        CmdOrder [] cmds = reMainOrder.getCmdOrderList();
        CmdOrder directInputCmd = null;

        for(int i = 0; i < cmds.length; i++) {
            if(parentCode == cmds[i].getCmdCode()) {
                directInputCmd = cmds[i];
            }
        }

        log.info("직접 입력 버튼 코드 >> " + directInputCmd);

        String msg = directInputCmd.getDirectTitle() + "\n\n아래 예시에 맞춰 입력해주세요.\n\nex) " + directInputCmd.getInputEx();

        SimpleText simpleTextVo = new SimpleText();
        simpleTextVo.setText(msg);

        ComponentSimpleText simpleText = new ComponentSimpleText();
        simpleText.setSimpleText(simpleTextVo);

        ArrayList<Object> outputs = new ArrayList<>();
        outputs.add(simpleText);

        SkillTemplate template = new SkillTemplate();
        template.setOutputs(outputs);

        return new ResponseDtoVerTwo().builder()
                .version("2.0")
                .template(template)
                .build();
    }



    public ResponseDtoVerTwo makerTransferBtnText() {

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
                .blockId(BLOCK_ID_EXIT)
                .build();


        QuickReply cancleBtn = QuickReply.builder()
                .label("취소")
                .messageText("명령 취소")
                .action("block")
                .blockId(BLOCK_ID_MAIN)
                .build();


        quickReplies.add(transferBtn);
        quickReplies.add(cancleBtn);
        template.setQuickReplies(quickReplies);

        return new ResponseDtoVerTwo().builder()
                .version("2.0")
                .template(template)
                .build();
    }



    public ResponseDtoVerTwo responserTransferCompleteText() {

        String msg = "명령을 전송하였습니다.\n\n또 다른 명령을 내리실려면 \"시바\" 버튼을 클릭하세요.";
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
                .label("시바")
                .action("message")
                .messageText("시바")
                .build();


        quickReplies.add(transferBtn);
        template.setQuickReplies(quickReplies);

        return new ResponseDtoVerTwo().builder()
                .version("2.0")
                .template(template)
                .build();
    }



    public ResponseDtoVerTwo responserCancleCompleteText() {

        String msg = "명령을 취소하였습니다.\n\n또 다른 명령을 내리실려면 \"시바\" 버튼을 클릭하세요.";
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
                .label("시바")
                .action("message")
                .messageText("시바")
                .build();


        quickReplies.add(transferBtn);
        template.setQuickReplies(quickReplies);

        return new ResponseDtoVerTwo().builder()
                .version("2.0")
                .template(template)
                .build();
    }



    // 사용 가능한 허브가 한 개 이상인 경우 허브 리스트 카드를 만들고 반환
    public ResponseDtoVerTwo makerHubsCard(ArrayList<HubInfoDto> hubs) {

        SimpleText text = new SimpleText();
        StringBuffer responseMsg = new StringBuffer("사용할 수 있는 허브 목록 입니다.\n\n");
        //responseMsg.append("(허브 번호).  (허브 이름) :: (허브 설명)\n\n");
        //responseMsg.append("(허브 번호). (허브 이름)\n\n");

        for (int i = 0; i < hubs.size(); i++) {
            //responseMsg.append((i + 1) + ". " + hubs.get(i).getHubName() + " :: " + hubs.get(i).getHubDescript() + "\n");
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
                    .blockId(BLOCK_ID_SELECT_HUB)
                    .build();
                quickReplies.add(quick);
        }

        SkillTemplate template = new SkillTemplate(outputs, quickReplies);

        return new ResponseDtoVerTwo().builder()
                .version("2.0")
                .template(template)
                .build();
    }



    public ResponseDtoVerTwo makerDevsCard(DevOrder[] devs) {

        SimpleText text = new SimpleText();
        StringBuffer responseMsg = new StringBuffer("사용할 수 있는 모듈 목록 입니다.\n\n");
        //responseMsg.append("(모듈 번호).  (모듈 이름)  ::  (식별명)\n\n");
        //responseMsg.append("(모듈 번호).  (모듈 이름)\n\n");
        for (int i = 0; i < devs.length; i++) {
            //responseMsg.append((i + 1) + ". " + devs[i].getDevName() + " :: " + devs[i].getDevIdentifier() + "\n");
            responseMsg.append((i + 1) + ". " + devs[i].getDevName() + "\n");
        }
        responseMsg.append("\n버튼을 클릭해주세요.");
        text.setText(responseMsg.toString());

        ComponentSimpleText simpleText = new ComponentSimpleText();
        simpleText.setSimpleText(text);

        ArrayList<Object> outputs = new ArrayList<Object>();
        outputs.add(simpleText);

        // 2. 버튼 꾸미기
        ArrayList<QuickReply> quickReplies = new ArrayList<QuickReply>();
        for (int i = 0; i < devs.length; i++) {
            QuickReply quick = QuickReply.builder()
                    .label(Integer.toString(i + 1))
                    .messageText("모듈 " + (i + 1) + "번")
                    .action("block")
                    .blockId(BLOCK_ID_SELECT_DEV)
                    .build();
             quickReplies.add(quick);
        }

        SkillTemplate template = new SkillTemplate(outputs, quickReplies);

        return new ResponseDtoVerTwo().builder()
                .version("2.0")
                .template(template)
                .build();
    }



    // providerId : redis에서 cmds 정보를 가져올 수 있다.
    // parentCode : 최근에 실행한 코드의 부모 코드 정보를 알 수 있다.
    public ResponseDtoVerTwo makerCmdsCard(String providerId, int parentCode) {

        // 최근 실행한 명령 코드 redis에 저장. 사용자에게 반환할 명령의 목록을 찾기 위해 쓰인다.
        MainOrder reMainOrder = mainOrderRepository.find(providerId);
        reMainOrder.setCurrentParentCode(parentCode);
        mainOrderRepository.save(reMainOrder);

        // 반환할 명령의 목록을 찾는 코드.
        CmdOrder[] cmds = reMainOrder.getCmdOrderList();
        CmdOrder[] returnCmds = null;
        int cnt = 0;
        for (int i = 0; i < cmds.length; i++) {
            if (parentCode == cmds[i].getParentCode()) {
                cnt++;
            }
        }
        if(cnt == 0) {
            // 만약 하위에 반활할 명령이 없다면. "전송" 버튼만 만들어서 사용자에게 보여준다.
            // returnCmds = new CmdOrder[1];       // 전송 버튼을 만들 객체 동적 할당.
            return this.makerTransferBtnText();
        } else {
            returnCmds = new CmdOrder[cnt + 2]; // 반환할 명령 + 전송 버튼의 배열 개수 만큼 동적 할당
        }
        cnt = 0;
        for (int i = 0; i < cmds.length; i++) {
            if (parentCode == cmds[i].getParentCode()) {   // 반환할 명령 코드들을 찾는 조건식
                returnCmds[cnt] = cmds[i];
                cnt++;
            }
        }

        // 카드 만들기
        SimpleText text = new SimpleText();
        StringBuffer responseMsg = new StringBuffer("사용할 수 있는 명령 목록 입니다.\n\n");
        //responseMsg.append("(명령 번호).  (명령 이름)\n\n");
        for (int i = 0; i < returnCmds.length - 2; i++) {
            responseMsg.append((i + 1) + ". " + returnCmds[i].getCmdName() + "\n");
        }
        responseMsg.append("\n버튼을 클릭해주세요.");
        text.setText(responseMsg.toString());

        ComponentSimpleText simpleText = new ComponentSimpleText();
        simpleText.setSimpleText(text);

        ArrayList<Object> outputs = new ArrayList<Object>();
        outputs.add(simpleText);

        // 버튼 꾸미기
        ArrayList<QuickReply> quickReplies = new ArrayList<QuickReply>();
        for (int i = 0; i < returnCmds.length - 2; i++) {
            QuickReply quick = QuickReply.builder()
                    .label(Integer.toString(i + 1))
                    .messageText("명령 " + (i + 1) + "번")
                    .action("block")
                    .blockId(returnCmds[i].getBlockId())
                    .build();

            quickReplies.add(quick);
        }

        QuickReply transferBtn = QuickReply.builder()
                .label("전송")
                .messageText("명령 전송")
                .action("block")
                .blockId(BLOCK_ID_EXIT)
                .build();

        QuickReply cancleBtn = QuickReply.builder()
                .label("취소")
                .action("block")
                .blockId(BLOCK_ID_MAIN)
                .messageText("취소")
                .build();

        quickReplies.add(transferBtn);
        quickReplies.add(cancleBtn);

        SkillTemplate template = new SkillTemplate(outputs, quickReplies);

        return new ResponseDtoVerTwo().builder()
                .version("2.0")
                .template(template)
                .build();
    }



    public ResponseDtoVerTwo makerButtonCmdsCard(String providerId, int parentCode) {

        MainOrder reMainOrder = mainOrderRepository.find(providerId);
        CmdOrder[] cmds = reMainOrder.getCmdOrderList();
        CmdOrder btnCmd = null;

        for (int i = 0; i < cmds.length; i++) {
            if (parentCode == cmds[i].getCmdCode()) {
                btnCmd = cmds[i];
            }
        }

        log.info("버튼 리스트 " + btnCmd.getBtnList());

        String[] btnNames = btnCmd.getBtnList().split(",");

        // 잘 분리되었는지 확인
        for (int i = 0; i < btnNames.length; i++) {
            System.out.println(i + " " + btnNames[i]);
        }

        // 카드 만들기
        SimpleText text = new SimpleText();
        StringBuffer responseMsg = new StringBuffer(btnCmd.getBtnTitle());
        //responseMsg.append("(버튼 번호). (버튼 이름)\n\n");

        for (int i = 0; i < btnNames.length; i++) {
            responseMsg.append((i + 1) + ". " + btnNames[i] + "\n");
        }
        responseMsg.append("\n버튼을 클릭해주세요.");
        text.setText(responseMsg.toString());

        ComponentSimpleText simpleText = new ComponentSimpleText();
        simpleText.setSimpleText(text);

        ArrayList<Object> outputs = new ArrayList<Object>();
        outputs.add(simpleText);

        // 버튼 꾸미기
        ArrayList<QuickReply> quickReplies = new ArrayList<QuickReply>();
        QuickReply[] quickReply = new QuickReply[btnNames.length];
        for (int i = 0; i < btnNames.length; i++) {
            QuickReply quick = QuickReply.builder()
                    .label(Integer.toString(i + 1))
                    .messageText("버튼 " + (i + 1) + "번")
                    .action("block")
                    .blockId(BLOCK_ID_CODE_LIST)
                    .build();
            try {
                quickReply[i] = (QuickReply) quick.clone();
                quickReplies.add(quickReply[i]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        SkillTemplate template = new SkillTemplate(outputs, quickReplies);

        return new ResponseDtoVerTwo().builder()
                .version("2.0")
                .template(template)
                .build();
    }




    // quick replies test
    public ResponseDtoVerTwo test(String msg) {

        SimpleText simpleTextVo = new SimpleText();
        simpleTextVo.setText(msg);

        ComponentSimpleText simpleText = new ComponentSimpleText();
        simpleText.setSimpleText(simpleTextVo);

        ArrayList<Object> outputs = new ArrayList<>();
        outputs.add(simpleText);

        SkillTemplate template = new SkillTemplate();
        template.setOutputs(outputs);

        ArrayList<QuickReply> quickReplies = new ArrayList<QuickReply>();

        int i = 7;
        QuickReply[] quickReply = new QuickReply[i];
        for(int j = 0; j < 7; j++) {
            QuickReply quick = QuickReply.builder()
                    .label(Integer.toString(j + 1))
                    .action("message")
                    .messageText("허브 " + (j + 1) + "번")
                    .build();

            try {
                quickReply[j] = (QuickReply) quick.clone();
                quickReplies.add(quickReply[j]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        log.info(quickReplies.toString());
        template.setQuickReplies(quickReplies);

        ResponseDtoVerTwo responseDtoVerTwo = ResponseDtoVerTwo.builder()
                .version("2.0")
                .template(template)
                .build();

        return responseDtoVerTwo;
    }
}
