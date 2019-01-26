package chatbot.api.user.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class KakaoAccount {

    @JsonProperty("has_email")
    private  boolean hasEmail;

    @JsonProperty("is_email_valid")
    private  boolean isEmailValid;

    @JsonProperty("is_email_verified")
    private  boolean isEmailVerified;

    @Email
    private String email;

    @JsonProperty("has_age_range")
    private  boolean hasAgeRange;

    @JsonProperty("has_birthday")
    private  boolean hasBirthday;

    @JsonProperty("has_gender")
    private  boolean hasGender;
}
