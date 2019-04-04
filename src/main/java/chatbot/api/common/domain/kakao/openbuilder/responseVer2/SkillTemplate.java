package chatbot.api.common.domain.kakao.openbuilder.responseVer2;

import chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseJsonFormat.Component.basicCard.Component;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SkillTemplate {

    //private BasicCard outputs;

    private ArrayList<Component> outputs;

    //private ArrayList<BasicCard> outputs;
    /*
    output : simpleText / simpleImage / basicCard / commnerceCard / listCard
     */

    //private ArrayList<QuickReply> quickReplies;
}
