package chatbot.api.user;


import chatbot.api.common.domain.ResponseDto;
import chatbot.api.user.domain.UserInfoDto;
import chatbot.api.common.security.UserPrincipal;
import chatbot.api.mappers.UserMapper;
import com.nimbusds.openid.connect.sdk.claims.UserInfo;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class UserController {

    private UserMapper userMapper;



    @GetMapping(value = "/user")
    public ResponseDto kakaoAuthoriaztion(@AuthenticationPrincipal UserPrincipal userPrincipal) {

        UserInfoDto userInfoDto = userMapper.getUser(userPrincipal.getId()).get();

        return ResponseDto.builder()
                .msg("userInfoDto information")
                .status(HttpStatus.OK)
                .data(userInfoDto)
                .build();
    }




    // 유효한 토큰인지 체크
    @GetMapping("/userToken")
    public ResponseDto checkValidToken(@AuthenticationPrincipal UserPrincipal UserPrincipal) {

        UserInfoDto userInfoDto = userMapper.getUserByUserId(UserPrincipal.getId());

        // 유저를 검색하지 못하면  -> hub에게 "not valid token" 메시지 전송
        if(userInfoDto == null) return ResponseDto.builder()
                                        .msg("fail : not valid token")
                                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .build();
        // 유저를 검색한다면      -> hub에게 "valid token" 메시지 전송
        else                    return ResponseDto.builder()
                                        .msg("success : valid token")
                                        .status(HttpStatus.OK)
                                        .build();
    }



    // 이메일로 사용자 조회
    @GetMapping("/users/{email}")
    public ResponseDto getUserByEmail(@PathVariable(value = "email") String email) {

        UserInfoDto user = userMapper.getUserByEmail(email);
        if(user == null) return ResponseDto.builder()
                                    .msg("fail : 해당 이메일로 가입된 유저는 없습니다.")
                                    .status(HttpStatus.OK)
                                    .data(null)
                                    .build();

        ResponseDto responseDto = ResponseDto.builder()
                .msg("success : " + email + " 이메일을 사용하는 유저 아이디는 " + user.getUserId().toString() + " 입니다.")
                .data(user.getUserId())
                .status(HttpStatus.OK)
                .build();

        return responseDto;
    }
}



/*
    // add user who control some hub
    @PostMapping("/hubuser")
    public ResponseDto insertHubUser(@RequestBody UserRegisterVo userRegisterVo, Long adminId) {
        // 원래는 adminId 대신 아래에 있는거 넣어야함
        // @AuthenticationPrincipal UserPrincipal userPrincipal, 원래 들어가야하는 인자


        // 지울거임..., adminId를 이렇게 초기화 시켜주는 이유 : 매개변수로 UserPrincipal 객체를 주기 때문에 나중에 헷갈리지 말라고
        adminId = userRegisterVo.getAdminId();
        System.out.println(adminId);
        System.out.println(userRegisterVo);

        // adminId 와 userRegisterVo ( hubId / email / adminId) 를 넘겨준다.
        if(!(userRegister.register(userRegisterVo, adminId))) {
            return ResponseDto.builder()
                    .msg("fail : no add user who control some hub")
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }


        // success : add user who control some hub
        return ResponseDto.builder()
                .msg("success : add user who control some hub")
                .status(HttpStatus.OK)
                .build();
    }
*/


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