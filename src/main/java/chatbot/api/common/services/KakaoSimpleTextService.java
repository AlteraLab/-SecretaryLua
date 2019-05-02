package chatbot.api.common.services;

import chatbot.api.common.domain.kakao.openbuilder.responseVer2.QuickReply;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseDtoVerTwo;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseJsonFormat.Component.simpleText.ComponentSimpleText;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseJsonFormat.Component.simpleText.SimpleText;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.SkillTemplate;
import chatbot.api.order.domain.CmdOrder;
import chatbot.api.order.domain.MainOrder;
import chatbot.api.order.repository.MainOrderRepository;
import chatbot.api.order.repository.MainOrderRepositoryImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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



    public ResponseDtoVerTwo makerDirectInputCard(String providerId, int parentCode) {

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
