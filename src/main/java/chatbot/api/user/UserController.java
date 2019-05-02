package chatbot.api.user;


import chatbot.api.common.domain.ResponseDto;
import chatbot.api.common.domain.kakao.openbuilder.RequestDto;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseDtoVerTwo;
import chatbot.api.common.services.KakaoBasicCardService;
import chatbot.api.mappers.HubMapper;
import chatbot.api.skillHub.domain.HubVo;
import chatbot.api.user.domain.UserInfoDto;
import chatbot.api.common.security.UserPrincipal;
import chatbot.api.mappers.UserMapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static chatbot.api.user.utils.UserConstants.*;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private HubMapper hubMapper;

    @Autowired
    private KakaoBasicCardService kakaoBasicCardService;



    // 메인 페이지에 보여질 사용 가능한 허브들에 대한 정보들을 반환하는 기능
    @GetMapping(value = "/user")
    public ResponseDto kakaoAuthoriaztion(@AuthenticationPrincipal UserPrincipal userPrincipal) {

        UserInfoDto userInfoDto = userMapper.getUser(userPrincipal.getId()).get();
        //UserInfoDto userInfoDto = userMapper.getUser(new Long(1)).get();

        List<HubVo> hubsInfoList;
        hubsInfoList = hubMapper.getHubsInfoByUserId(userInfoDto.getUserId());
        //hubsInfoList = hubMapper.getHubsInfoByUserId(userPrincipal.getId());
        // 바로 위, 일단 주석... 야밤에 코드 작성하니까 잘못 작성한듯...
        //hubsInfoList = hubMapper.getHubsInfoByUserId(new Long(1));

        return ResponseDto.builder()
                .msg("userInfoDto information")
                .status(HttpStatus.OK)
                .data(new Object(){
                    public UserInfoDto user = userInfoDto;
                    public List<HubVo> hubs = hubsInfoList;
                })
                .build();
    }



    // 유효한 토큰인지 체크
    @GetMapping("/userToken")
    public ResponseDto checkValidToken(@AuthenticationPrincipal UserPrincipal UserPrincipal) {

        UserInfoDto userInfoDto = userMapper.getUserByUserId(UserPrincipal.getId());
        //UserInfoDto userInfoDto = userMapper.getUserByUserId(new Long(5));

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
                .status(HttpStatus.EXPECTATION_FAILED)
                .data(null)
                .build();

        // log
        log.info(user.toString());

        ResponseDto responseDto = ResponseDto.builder()
                .msg(SUCCESS_MSG_SELECT_BY_EMAIL)
                .status(HttpStatus.OK)
                .data(null)
                .build();

        return responseDto;
    }



    // 0. 사용자가 "등록"을 발화로 입력하면, 사용자 id를 바인딩하여서 react app url 반환
    @PostMapping("/signUp")
    public ResponseDtoVerTwo requestSignUp(@RequestBody RequestDto requestDto) {

        // 1. 해당 사용자의 id 값이 이미 데이터베이스에 저장되어 있는지 확인.
        // db에서 id값을 바탕으로 user정보를 select 해오는 코드


        // 2-1. 확인 결과 이미 있는 id 값이면, "이미 등록된 아이디 입니다." 리턴
/*        if(requestDto != null) {
            return alreadyJoinedResponser.responser();
        }*/

        // 2-2. 확인 결과 없는 id 값이면, "등록 하세요" 실시!
        return kakaoBasicCardService.responserRequestJoin(requestDto.getUserRequest().getUser().getId());
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