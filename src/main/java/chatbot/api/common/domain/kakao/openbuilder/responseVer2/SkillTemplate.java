package chatbot.api.common.domain.kakao.openbuilder.responseVer2;

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

    private ArrayList<Object> outputs;

    private ArrayList<QuickReply> quickReplies;
    /*
    output : simpleText / simpleImage / basicCard / commnerceCard / listCard
     */
}
