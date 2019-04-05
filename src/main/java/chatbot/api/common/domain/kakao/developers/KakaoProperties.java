package chatbot.api.common.domain.kakao.developers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class KakaoProperties {

    private String nickname;

    @JsonProperty("profile_image")
    private String profileImage;

    @JsonProperty("thumbnail_image")
    private String thumbnailImage;
}
