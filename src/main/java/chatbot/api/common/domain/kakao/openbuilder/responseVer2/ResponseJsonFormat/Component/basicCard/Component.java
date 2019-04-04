package chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseJsonFormat.Component.basicCard;

import chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseJsonFormat.Component.basicCard.BasicCard;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseJsonFormat.Component.simpleText.SimpleText;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Component {

    private BasicCard basicCard;
}
