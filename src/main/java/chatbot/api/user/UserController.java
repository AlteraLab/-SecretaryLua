package chatbot.api.user;


import chatbot.api.common.RequestDto;
import chatbot.api.common.ResponseDto;
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

    @PostMapping(value = "/user")
    public void test()
    {
        return ;
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
}
