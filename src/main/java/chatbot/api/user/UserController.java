package chatbot.api.user;


import chatbot.api.common.domain.ResponseDto;
import chatbot.api.mappers.HubMapper;
import chatbot.api.skillHub.domain.HubsVo;
import chatbot.api.user.domain.UserInfoDto;
import chatbot.api.common.security.UserPrincipal;
import chatbot.api.mappers.UserMapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static chatbot.api.user.utils.UserConstants.*;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private HubMapper hubMapper;


    // 메인 페이지에 보여질 사용 가능한 허브들에 대한 정보들을 반환하는 기능
    @GetMapping(value = "/user")
    public ResponseDto kakaoAuthoriaztion(@AuthenticationPrincipal UserPrincipal userPrincipal) {

        // 1. 유저 디비 createdAt / updatedAt 추가 후, 기존 기능들 정상적으로 수행되는지 확인
        UserInfoDto userInfoDto = userMapper.getUser(userPrincipal.getId()).get();
        //UserInfoDto userInfoDto = userMapper.getUser(userId).get();  // 실험용

        // 2. 사용자가 사용할 수 있는 허브들에 대해서 hub + role 조인해서 데이터들 모두 메모리로 가져오기
        List<HubsVo> hubsInfoList;
        hubsInfoList = hubMapper.getHubsInfoByUserId(userPrincipal.getId());
        //hubsInfoList = hubMapper.getHubsInfoByUserId(userId);

        // 3. data set
        Map<Object, List<HubsVo>> data = new HashMap<Object, List<HubsVo>>();
        data.put(userInfoDto, hubsInfoList);

        return ResponseDto.builder()
                .msg("userInfoDto information")
                .status(HttpStatus.OK)
                .data(data)
                .build();
    }


    // 유효한 토큰인지 체크
    @GetMapping("/userToken")
    public ResponseDto checkValidToken(@AuthenticationPrincipal UserPrincipal UserPrincipal) {

        //UserInfoDto userInfoDto = userMapper.getUserByUserId(UserPrincipal.getId());
        UserInfoDto userInfoDto = userMapper.getUserByUserId(new Long(5));

        // 유저를 검색하지 못하면  -> hub에게 "not valid token" 메시지 전송
        if (userInfoDto == null) return ResponseDto.builder()
                .msg(FAIL_MSG_TOKEN)
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
            // 유저를 검색한다면      -> hub에게 "valid token" 메시지 전송
        else return ResponseDto.builder()
                .msg(SUCCESS_MSG_TOKEN)
                .status(HttpStatus.OK)
                .build();
    }


    // 이메일로 사용자 조회
    @GetMapping("/user/{email}")
    public ResponseDto getUserByEmail(@PathVariable(value = "email") String email) {

        UserInfoDto user = userMapper.getUserByEmail(email);
        if (user == null) return ResponseDto.builder()
                .msg(FAIL_MSG_SELECT_BY_EMAIL)
                .status(HttpStatus.NO_CONTENT)
                .data(null)
                .build();
        // NO_CONTENT : 요청에 대해서 보내줄 수 있는 콘텐츠가 없지만, 헤더는 의미있을 수 있다.

        // log
        log.info(user.toString());

        ResponseDto responseDto = ResponseDto.builder()
                .msg(SUCCESS_MSG_SELECT_BY_EMAIL)
                .status(HttpStatus.OK)
                .data(null)
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