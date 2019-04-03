package chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseJsonFormat.Component.basicCard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

    private String imageUrl;

    private String nickname;
}
