package chatbot.api.common.services;

import chatbot.api.common.domain.kakao.openbuilder.responseVer2.QuickReply;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseDtoVerTwo;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseJsonFormat.Component.basicCard.BasicCard;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseJsonFormat.Component.basicCard.Button;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseJsonFormat.Component.basicCard.ComponentBasicCard;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseJsonFormat.Component.simpleText.ComponentSimpleText;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseJsonFormat.Component.simpleText.SimpleText;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.SkillTemplate;
import chatbot.api.order.domain.CmdOrder;
import chatbot.api.order.domain.DevOrder;
import chatbot.api.order.domain.MainOrder;
import chatbot.api.order.repository.MainOrderRepositoryImpl;
import chatbot.api.skillHub.domain.HubInfoDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static chatbot.api.order.utils.OrderConstans.*;

@Service
@Slf4j
@AllArgsConstructor
public class KakaoBasicCardService {

    private MainOrderRepositoryImpl mainOrderRepository;


    // 사용 가능한 허브가 한 개 이상인 경우 허브 리스트 카드를 만들고 반환
    public ResponseDtoVerTwo makerHubsCard(ArrayList<HubInfoDto> hubs) {

        SimpleText text = new SimpleText();
        StringBuffer responseMsg = new StringBuffer("사용할 수 있는 허브 목록 입니다.\n\n");
        //responseMsg.append("(허브 번호).  (허브 이름) :: (허브 설명)\n\n");
        responseMsg.append("(허브 번호). (허브 이름)\n\n");

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
        QuickReply[] quickReply = new QuickReply[hubs.size()];
        for (int i = 0; i < hubs.size(); i++) {

            QuickReply quick = QuickReply.builder()
                    .label(Integer.toString(i + 1))
                    .action("block")
                    .messageText("허브 " + (i + 1) + "번")
                    .blockId(BLOCK_ID_SELECT_HUB)
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


    public ResponseDtoVerTwo makerDevsCard(DevOrder[] devs) {

        SimpleText text = new SimpleText();
        StringBuffer responseMsg = new StringBuffer("사용할 수 있는 모듈 목록 입니다.\n\n");
        //responseMsg.append("(모듈 번호).  (모듈 이름)  ::  (식별명)\n\n");
        responseMsg.append("(모듈 번호).  (모듈 이름)\n\n");
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
        QuickReply[] quickReply = new QuickReply[devs.length];
        for (int i = 0; i < devs.length; i++) {
            QuickReply quick = QuickReply.builder()
                    .label(Integer.toString(i + 1))
                    .messageText("모듈 " + (i + 1) + "번")
                    .action("block")
                    .blockId(BLOCK_ID_SELECT_DEV)
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


    // providerId : redis에서 cmds 정보를 가져올 수 있다.
    // parentCode : 최근에 실행한 코드의 부모 코드 정보를 알 수 있다.
    public ResponseDtoVerTwo makerCmdsCard(String providerId, int parentCode) {

        MainOrder reMainOrder = mainOrderRepository.find(providerId);
        reMainOrder.setCurrentParentCode(parentCode);
        mainOrderRepository.save(reMainOrder);

        CmdOrder[] cmds = reMainOrder.getCmdOrderList();

        int cnt = 0;
        for (int i = 0; i < cmds.length; i++) {
            if (parentCode == cmds[i].getParentCode()) {
                cnt++;
            }
        }

        CmdOrder[] returnCmds = new CmdOrder[cnt];
        cnt = 0;
        for (int i = 0; i < cmds.length; i++) {
            if (parentCode == cmds[i].getParentCode()) {
                returnCmds[cnt] = cmds[i];
                cnt++;
            }
        }
        log.info("1. returnCmds >> " + returnCmds);


        // 카드 만들기
        SimpleText text = new SimpleText();
        StringBuffer responseMsg = new StringBuffer("사용할 수 있는 명령 목록 입니다.\n\n");
        responseMsg.append("(명령 번호).  (명령 이름)\n\n");
        for (int i = 0; i < returnCmds.length; i++) {
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
        QuickReply[] quickReply = new QuickReply[returnCmds.length + 1];
        for (int i = 0; i < returnCmds.length; i++) {
            QuickReply quick = QuickReply.builder()
                    .label(Integer.toString(i + 1))
                    .messageText("명령 " + (i + 1) + "번")
                    .action("block")
                    .blockId(returnCmds[i].getBlockId())
                    .build();
            cnt = i + 1;
            try {
                quickReply[i] = (QuickReply) quick.clone();
                quickReplies.add(quickReply[i]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        QuickReply quick = QuickReply.builder()
                .label("전송")
                .messageText("명령 전송")
                .action("block")
                .blockId(BLOCK_ID_EXIT)
                .build();
        try {
            quickReply[cnt] = (QuickReply) quick.clone();
            quickReplies.add(quickReply[cnt]);
        } catch (Exception e) {
            e.printStackTrace();
        }

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
        responseMsg.append("(버튼 번호). (버튼 이름)\n\n");

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



    public ResponseDtoVerTwo responserRequestJoin(String id) {
        ArrayList<Button> buttons = new ArrayList<Button>();
        buttons.add(Button.builder()
                .action("webLink")
                .label("Sign Up")
                .webLinkUrl("http://203.250.32.29:3000?id=" + id)
                .build());
        BasicCard basicCardVo = BasicCard.builder()
                .title("등록")
                .description("버튼을 클릭하세요.\n" + "웹페이지에서 폼을 입력하시고 확인 버튼을 누르세요.")
                .thumbnail(null)
                .profile(null)
                .social(null)
                .buttons(buttons)
                .build();

        ComponentBasicCard basicCard = new ComponentBasicCard();
        basicCard.setBasicCard(basicCardVo);

        ArrayList<Object> outputs = new ArrayList<Object>();
        outputs.add(basicCard);

        SkillTemplate template = new SkillTemplate();
        template.setOutputs(outputs);

        ResponseDtoVerTwo responseDtoVerTwo = ResponseDtoVerTwo.builder()
                .version("2.0")
                .template(template)
                .build();

        return responseDtoVerTwo;
    }


    // 사용자가 발화를 넘겼을때 appUserId가 null 일때 회원가입 링크를 response 해준다.
    // 아직 구현 안해줌.
    public ResponseDtoVerTwo responserRequestPreSignUp() {

        ArrayList<Button> buttons = new ArrayList<Button>();
        buttons.add(Button.builder()
                .action("webLink")
                .label("Sign Up")
                .webLinkUrl("http://203.250.32.29:3000/")  // 가입 링크
                .build());

        BasicCard basicCardVo = BasicCard.builder()
                .title("Sign Up")
                .description("회원가입을 먼저 하세요.")
                .thumbnail(null)
                .profile(null)
                .social(null)
                .buttons(buttons)
                .build();

        ComponentBasicCard basicCard = new ComponentBasicCard();
        basicCard.setBasicCard(basicCardVo);

        ArrayList<Object> outputs = new ArrayList<Object>();
        outputs.add(basicCard);

        SkillTemplate template = new SkillTemplate();
        template.setOutputs(outputs);

        ResponseDtoVerTwo responseDtoVerTwo = ResponseDtoVerTwo.builder()
                .version("2.0")
                .template(template)
                .build();

        return responseDtoVerTwo;
    }
}

