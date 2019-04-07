package chatbot.api.common.services;

import chatbot.api.common.domain.kakao.openbuilder.responseVer2.QuickReply;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseDtoVerTwo;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseJsonFormat.Component.simpleText.ComponentSimpleText;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseJsonFormat.Component.simpleText.SimpleText;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.SkillTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
public class KakaoSimpleTextResponseService {



    // 짧은 메시지 responser
    public ResponseDtoVerTwo responserShortMsg(String msg) {

        SimpleText simpleTextVo = new SimpleText();
        simpleTextVo.setText(msg);

        ComponentSimpleText simpleText = new ComponentSimpleText();
        simpleText.setSimpleText(simpleTextVo);

        ArrayList<Object> outputs = new ArrayList<>();
        outputs.add(simpleText);

        SkillTemplate template = new SkillTemplate();
        template.setOutputs(outputs);

        ResponseDtoVerTwo responseDtoVerTwo = ResponseDtoVerTwo.builder()
                .version("2.0")
                .template(template)
                .build();

        return responseDtoVerTwo;
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
