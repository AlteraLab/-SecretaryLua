package chatbot.api.user;


import chatbot.api.user.domain.KakaoAuthCodeInfo;
import chatbot.api.user.domain.KakaoUserInfoDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.websocket.server.PathParam;

/*
service
1. response BotUserKey
2. response msg_enroll_guide
3. response msg_enroll_result
 */

@RestController
@AllArgsConstructor
public class UserController {

    private RestTemplate restTemplate;

    /*
    // lua response botUserKey service
    @PostMapping(value = "/user/key")
    public ResponseDto getBotUserKey(@RequestBody RequestDto requestDto) {
        String msg = requestDto.getUserRequest().getUser().getId();
        return ResponseDto.builder().msg(msg).status(HttpStatus.OK).data(null).build();
    }

    // lua response ENROLL_GUIDE
    @PostMapping(value = "/user/enroll/guide")
    public ResponseDto getGuideMsg(@RequestBody RequestDto requestDto) {
        return ResponseDto.builder().msg(UserConstants.GUIDE_ENROLL).status(HttpStatus.OK).data(null).build();
    }

    // test
    // lua response ENROLL_RESULT
    @PostMapping(value = "/user/enroll/result")
    public ResponseDto getEnrollResult(@RequestBody RequestDto requestDto) {
        String kakaoUserId = requestDto.getUserRequest().getUser().getId();
        String utterance = requestDto.getUserRequest().getUtterance();

        String userIp = null;
        String userPassword = utterance;

        int firstIndexForIp = userPassword.indexOf(" ");
        int lastIndexForIp = userPassword.indexOf(",");
        int firstIndexForPw = lastIndexForIp + 7;
        int lastIndexForPw = userPassword.length();

        userIp = userPassword.substring(firstIndexForIp, lastIndexForIp);
        userPassword = userPassword.substring(firstIndexForPw, lastIndexForPw);

        System.out.println(userIp + " " + userPassword);

        return ResponseDto.builder().msg(UserConstants.CREATE_USER_SUCCESS).status(HttpStatus.OK).data(null).build();
    }

    // oauth2 인증시, 호출되는 API
    @PostMapping(value = "/user/login")
    public LoginResponseDto enrollUser(@RequestBody LoginRequestDto loginRequestDto) {
        // 값 확인
        System.out.println("beforeIp  : " + loginRequestDto.getBeforeIp());
        System.out.println("currentIp : " + loginRequestDto.getCurrentIp());
        System.out.println("port      : " + loginRequestDto.getPort());
        return LoginResponseDto.builder().test(null).build();
    }*/

    @PostMapping(value = "/kakaotest/{auth_code}")
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
    }

    @GetMapping(value = "/kakaoauth")
    public void kakaoAuthoriaztion(@PathParam("code") String authCode) {
        System.out.println(authCode);
    }
}
