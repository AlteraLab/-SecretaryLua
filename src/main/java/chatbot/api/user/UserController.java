package chatbot.api.user;


import chatbot.api.common.RequestDto;
import chatbot.api.common.ResponseDto;
import chatbot.api.user.domain.LoginRequestDto;
import chatbot.api.user.domain.LoginResponseDto;
import chatbot.api.user.utils.UserConstants;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.Request;

/*
service
1. response BotUserKey
2. response msg_enroll_guide
3. response msg_enroll_result
 */

@RestController
public class UserController {

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

        // 만약, 모든 출력이 정상적으로 되었다면.., db 모델링 이후 코드 작성
        /*
        if(이번에 넘어온 beforeIp != null)
        {
            if(db에 저장되어진 currentIp컬럼 값들 중에, 이번에 넘어온 beforeIp와 동일한 것이 있다면)
            {
                1. db에 저장되어진 currentIp를 beforeIp 컬럼 영역으로 업데이트
                2. 이번에 넘어온 currentIp값을 db의 currentIp 컬럼 영역으로 업데이트
                3. port도 업데이트
            }
        }
        else
        {
            1. currentIp 값을 db에 업데이터
            2. port도 업데이트
        }
        // 중첩된 코드 : 모듈화해서 적용하기

        허브 서버에 보낼 LoginResponseDto에 어떤 값 실어서 보낼지 이야기하기
         */


        return LoginResponseDto.builder().test(null).build();
    }
}
