package chatbot.api.common.domain.kakao.developers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class KakaoUserInfoDto{

    private String id;

    public void setId(Long id){
        this.id=id.toString();
    }

    @JsonProperty("has_signed_up")
    private boolean hasSignedUp;

    private KakaoProperties properties;

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;
}
