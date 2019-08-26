package chatbot.api.user;


import chatbot.api.common.domain.ResponseDTO;
import chatbot.api.common.domain.kakao.openbuilder.RequestDTO;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseVerTwoDTO;
import chatbot.api.common.services.KakaoBasicCardService;
import chatbot.api.mappers.HubMapper;
import chatbot.api.skillhub.domain.HubVO;
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
    public ResponseDTO kakaoAuthoriaztion(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        log.info("" + userPrincipal);
        UserInfoDto userInfoDto = userMapper.getUser(userPrincipal.getId()).get();
        //UserInfoDto userInfoDto = userMapper.getUser(new Long(1)).get();

        List<HubVO> hubsInfoList;
        hubsInfoList = hubMapper.getHubsInfoByUserId(userInfoDto.getUserId());
        log.info("HubsInfo -> " + hubsInfoList);
        //hubsInfoList = hubMapper.getHubsInfoByUserId(new Long(1));
        return ResponseDTO.builder()
                .msg("userInfoDto information")
                .status(HttpStatus.OK)
                .data(new Object(){
                    public UserInfoDto user = userInfoDto;
                    public List<HubVO> hubs = hubsInfoList;
                })
                .build();
    }



    // 유효한 토큰인지 체크
    @GetMapping("/userToken")
    public ResponseDTO checkValidToken(@AuthenticationPrincipal UserPrincipal UserPrincipal) {

        UserInfoDto userInfoDto = userMapper.getUserByUserId(UserPrincipal.getId());
        //UserInfoDto userInfoDto = userMapper.getUserByUserId(new Long(5));

        // 유저를 검색하지 못하면  -> hub에게 "not valid token" 메시지 전송
        if (userInfoDto == null) return ResponseDTO.builder()
                .msg(FAIL_MSG_TOKEN)
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
            // 유저를 검색한다면      -> hub에게 "valid token" 메시지 전송
        else return ResponseDTO.builder()
                .msg(SUCCESS_MSG_TOKEN)
                .status(HttpStatus.OK)
                .build();
    }



    // 이메일로 사용자 조회
    @GetMapping("/user/{email}")
    public ResponseDTO getUserByEmail(@PathVariable(value = "email") String email) {

        UserInfoDto user = userMapper.getUserByEmail(email);
        if (user == null) return ResponseDTO.builder()
                .msg(FAIL_MSG_SELECT_BY_EMAIL)
                .status(HttpStatus.EXPECTATION_FAILED)
                .data(null)
                .build();

        // log
        log.info(user.toString());

        ResponseDTO responseDto = ResponseDTO.builder()
                .msg(SUCCESS_MSG_SELECT_BY_EMAIL)
                .status(HttpStatus.OK)
                .data(null)
                .build();

        return responseDto;
    }
}