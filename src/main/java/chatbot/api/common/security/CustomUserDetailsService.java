package chatbot.api.common.security;

import chatbot.api.mappers.UserMapper;
import chatbot.api.user.domain.UserInfoDto;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private UserMapper userMapper;

    //JWT AuthenticationFilter로 사용됨
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email){
            /*throws UsernameNotFoundException {
                UserInfoDto userInfoDto = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("UserInfoDto not found with email : " + email)
                );*/

        return null;
    }

    @Transactional
    public UserDetails loadUserById(Long id) {

        //사용자 id를 통해 사용자 조회
        UserInfoDto userInfoDto = userMapper.getUser(id).orElseThrow(
                () -> new ResourceNotFoundException()
        );

        //UserPrincipal 생성
        return UserPrincipal.create(userInfoDto);
    }
}
