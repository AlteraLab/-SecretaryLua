package chatbot.api.common.domain.kakao.developers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class KakaoAccount {

    @JsonProperty("has_email")
    private boolean hasEmail; //email로 카카오 계정을 생성한 경우 true

    //email이 false인 경우 is_email_valid, is_email_verified, emaail은 미포함
    @JsonProperty("is_email_valid")
    private boolean isEmailValid; //이메일 실소유자로부터 무효화 되면 false가 됨

    @JsonProperty("is_email_verified")
    private boolean isEmailVerified;

    @JsonProperty("email_needs_agreement")
    private boolean emailNeedsAgreement;

    private String email; //전화번호로 카카오 계정을 생성한 경우 email 미존재
}
