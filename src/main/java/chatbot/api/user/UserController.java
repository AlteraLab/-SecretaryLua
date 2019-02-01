package chatbot.api.user;


import chatbot.api.common.domain.ResponseDto;
import chatbot.api.user.domain.UserInfoDto;
import chatbot.api.common.security.UserPrincipal;
import chatbot.api.mappers.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class UserController {

    private UserMapper userMapper;

    /*@PostMapping(value = "/kakaotest/{auth_code}")
    public KakaoUserInfoDto kakaoAuth(@PathVariable("auth_code") String authCode) {
        System.out.println(authCode);

        //authorization code를 통하여 카카오 사용자 토큰 발급
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://kauth.kakao.com/oauth/token");

        MultiValueMap<String, String> parts = new LinkedMultiValueMap<>();
        parts.add("grant_type", "authorization_code");
        parts.add("client_id", "83471680d720ccbf5f678b8841136546");
        parts.add("redirect_uri", "http://localhost:3000/");
        parts.add("code", authCode);

        KakaoAuthCodeInfo res = restTemplate.postForObject(builder.toUriString(), parts, KakaoAuthCodeInfo.class);
        System.out.println("accessToken: "+ res.getAccessToken());

        //사용자 정보 요청
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.set("Authorization","Bearer"+" "+res.getAccessToken());
        builder = UriComponentsBuilder.fromHttpUrl("https://kapi.kakao.com/v2/user/me");
        HttpEntity<String> entity = new HttpEntity<>("parameters",headers);
        KakaoUserInfoDto kakaoUserInfo = restTemplate.postForObject(builder.toUriString(), entity, KakaoUserInfoDto.class);
        System.out.println("id: "+ kakaoUserInfo.getId());

        return kakaoUserInfo;
    }*/

    @GetMapping(value = "/user")
    public ResponseDto kakaoAuthoriaztion(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        UserInfoDto userInfoDto = userMapper.getUser(userPrincipal.getId()).get();
        return ResponseDto.builder()
                .msg("userInfoDto information")
                .status(HttpStatus.OK)
                .data(userInfoDto)
                .build();
    }
}
