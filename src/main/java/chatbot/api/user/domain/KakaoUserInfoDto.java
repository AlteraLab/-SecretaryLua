package chatbot.api.user.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class KakaoUserInfoDto implements OAuth2User {

    //사용자 권한
    private List<GrantedAuthority> authorities =
            AuthorityUtils.createAuthorityList("ROLE_USER");

    private Map<String, Object> attributes;

    private long id;

    @JsonProperty("has_signed_up")
    private boolean hasSignedUp;

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        if (this.attributes == null) {
            this.attributes = new HashMap<>();
            this.attributes.put("id", this.getId());
            this.attributes.put("name", "12fsf");
        }
        return attributes;
    }

    @Override
    public String getName() {
        return "11312";
    }
}
