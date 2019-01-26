package chatbot.api.common.security.oauth2;

import chatbot.api.common.security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.stereotype.Service;

//oauth2 provider로 부터 access Token을 얻은 이후 호출
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;


}
