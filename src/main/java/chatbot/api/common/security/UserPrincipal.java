package chatbot.api.common.security;


import chatbot.api.user.domain.UserInfoDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

//@AllArgsConstructor  문제의 주범...
@Builder
@Data
public class UserPrincipal implements UserDetails, OAuth2User {

    private Long id;

    private String username;

    //private Collection<? extends GrantedAuthority> authorities;

    //사용자 정보로부터 UserPrincipal 생성
    public static UserPrincipal create(UserInfoDto userInfoDto) {
        /*List<GrantedAuthority> authorities = userInfoDto.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getName().name())
        ).collect(Collectors.toList());*/

        return UserPrincipal.builder()
                .id(userInfoDto.getUserId())
                .username(userInfoDto.getName())
                //.authorities(authorities)
                .build();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return String.valueOf(id);
    }
}
