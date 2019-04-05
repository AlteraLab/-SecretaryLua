package chatbot.api.common.security.oauth2;

import chatbot.api.user.domain.UserInfoDto;
import chatbot.api.common.security.UserPrincipal;
import chatbot.api.mappers.UserMapper;
import chatbot.api.common.domain.kakao.developers.KakaoUserInfoDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequestEntityConverter;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

/**
 * oauth2 provider로 부터 access Token을 얻은 이후 호출
 * @author GYOJUN AN
 */
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private static final String MISSING_USER_INFO_URI_ERROR_CODE = "missing_user_info_uri";

    private static final String MISSING_USER_NAME_ATTRIBUTE_ERROR_CODE = "missing_user_name_attribute";

    private static final String INVALID_USER_INFO_RESPONSE_ERROR_CODE = "invalid_user_info_response";

    private static final ParameterizedTypeReference<Map<String, Object>> PARAMETERIZED_RESPONSE_TYPE =
            new ParameterizedTypeReference<Map<String, Object>>() {};

    private RestOperations restOperations;

    private Converter<OAuth2UserRequest, RequestEntity<?>> requestEntityConverter = new OAuth2UserRequestEntityConverter();

    @Autowired
    private UserMapper userMapper;

    public CustomOAuth2UserService(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler());
        this.restOperations = restTemplate;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        //provider의 tokenUri 검사
        if (!StringUtils.hasText(userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri())) {
            OAuth2Error oauth2Error = new OAuth2Error(
                    MISSING_USER_INFO_URI_ERROR_CODE,
                    "Missing required UserInfo Uri in UserInfoEndpoint for Client Registration: " +
                            userRequest.getClientRegistration().getRegistrationId(),
                    null
            );
            throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString());
        }

        //provider의 user-name-attribute 값 검사
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();
        if (!StringUtils.hasText(userNameAttributeName)) {
            OAuth2Error oauth2Error = new OAuth2Error(
                    MISSING_USER_NAME_ATTRIBUTE_ERROR_CODE,
                    "Missing required \"user name\" attribute name in UserInfoEndpoint for Client Registration: " +
                            userRequest.getClientRegistration().getRegistrationId(),
                    null
            );
            throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString());
        }

        RequestEntity<?> request = this.requestEntityConverter.convert(userRequest);

        ResponseEntity<Map<String, Object>> response;

        try {
            //https://kapi.kakao.com/v2/user/me로 Authorization헤더 포함하여 사용자 정보 요청
            response = this.restOperations.exchange(request, PARAMETERIZED_RESPONSE_TYPE);
        } catch (OAuth2AuthorizationException ex) {
            OAuth2Error oauth2Error = ex.getError();
            StringBuilder errorDetails = new StringBuilder();
            errorDetails.append("Error details: [");
            errorDetails.append("UserInfo Uri: ").append(
                    userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri());
            errorDetails.append(", Error Code: ").append(oauth2Error.getErrorCode());
            if (oauth2Error.getDescription() != null) {
                errorDetails.append(", Error Description: ").append(oauth2Error.getDescription());
            }
            errorDetails.append("]");
            oauth2Error = new OAuth2Error(INVALID_USER_INFO_RESPONSE_ERROR_CODE,
                    "An error occurred while attempting to retrieve the UserInfo Resource: " + errorDetails.toString(), null);
            throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString(), ex);
        } catch (RestClientException ex) {
            OAuth2Error oauth2Error = new OAuth2Error(INVALID_USER_INFO_RESPONSE_ERROR_CODE,
                    "An error occurred while attempting to retrieve the UserInfo Resource: " + ex.getMessage(), null);
            throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString(), ex);
        }

        Map<String, Object> userAttributes = response.getBody();//response body에 사용자 정보들 위치

        //map을 KakaoUserInfoDto로 변환
        final ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper
        KakaoUserInfoDto kakaoUserInfo = mapper.convertValue(userAttributes, KakaoUserInfoDto.class);

        System.out.println(kakaoUserInfo.toString());

        OAuth2User oauth2User = processOAuth2User(kakaoUserInfo);
        System.out.println(oauth2User.toString());
        return oauth2User;

        /*Set<GrantedAuthority> authorities = Collections.singleton(new OAuth2UserAuthority(userAttributes));


        return new DefaultOAuth2User(authorities, userAttributes, userNameAttributeName);*/
    }

    private OAuth2User processOAuth2User(KakaoUserInfoDto kakaoUserInfo) {
        Optional<UserInfoDto> userOptional = userMapper.getUserByProviderId(kakaoUserInfo.getId());
        UserInfoDto userInfoDto;
        if(userOptional.isPresent()) { //사용자 정보 업데이트
            userInfoDto = userOptional.get();
            userInfoDto = updateExistingUser(userInfoDto, kakaoUserInfo);
        } else { //등록이 안된 경우 새로 등록
            userInfoDto = registerNewUser(kakaoUserInfo);
        }

        return UserPrincipal.create(userInfoDto);
    }

    //새로운 사용자 등록
    private UserInfoDto registerNewUser(KakaoUserInfoDto kakaoUserInfo) {
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setProviderId(kakaoUserInfo.getId());
        //userInfoDto.setName(kakaoUserInfo.getProperties().getNickname());
        userInfoDto.setName("test"); //추후에 이름 변경해야
        userInfoDto.setEmail(kakaoUserInfo.getKakaoAccount().getEmail());
        userInfoDto.setProfileImage(kakaoUserInfo.getProperties().getProfileImage());
        userMapper.save(userInfoDto);
        userInfoDto = userMapper.getUserByProviderId(kakaoUserInfo.getId()).get(); //추후에 최적화를 위한다면 provider_id가 PK가 되어야만 함
        return userInfoDto;
    }

    //기존 사용자 정보 업데이트
    private UserInfoDto updateExistingUser(UserInfoDto existingUserInfoDto, KakaoUserInfoDto kakaoUserInfo) {
        //existingUserInfoDto.setName(kakaoUserInfo.getProperties().getNickname());
        existingUserInfoDto.setName("test"); //추후에 이름 변경해야
        existingUserInfoDto.setProfileImage(kakaoUserInfo.getProperties().getProfileImage());
        existingUserInfoDto.setEmail(kakaoUserInfo.getKakaoAccount().getEmail());
        userMapper.update(existingUserInfoDto);
        return existingUserInfoDto;
    }

}