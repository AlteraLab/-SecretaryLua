package chatbot.api.email;

import chatbot.api.email.domain.SignUpDataVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

 /*
    userController에 옮김

    // 0. 사용자가 "등록"을 발화로 입력하면, 사용자 id를 바인딩하여서 react app url 반환
    @PostMapping("/signUp")
    public ResponseDto requestSignUp(@RequestBody RequestDto requestDto) {

        // 1, 해당 사용자의 id 값이 이미 데이터베이스에 저장되어 있는지 확인.

        // 2-1. 확인 결과 이미 있는 id 값이면, "이미 등록된 아이디 입니다." 리턴

        // 2-2. 확인 결과 없는 id 값이면, "등록 하세요" 실시!


        return ResponseDto.builder().build();
    }*/



    // 1. react 앱으로 사용자가 입력한 폼을 받아들이고 데이터를 세션화 한다. 그리고 유저에게 이메일을 전송한다.
    /*
    1-1. 사용자가 입력한 값을 세션으로 만들어서 저장한다. 유지시간은 3분
    1-2. 사용자에게 이메일을 보낸다.

    받는 데이터 목록
    // 1. id 값
    // 2. email
    // 3. password
     */
    @PostMapping("/signUp/session")
    public void receiveSignUpData(@RequestBody SignUpDataVo signUpDataVo) {

        // 1. react app 에서 보낸 데이터를 세션화해서 저장한다.

        // 2. 이메일 발송
        sendAuthEmail("ejsvk3284@gmail.com");
    }



    public void sendAuthEmail(String email) {
        // 1. 사용자에게 이메일을 보낸다.
    }



    // 2. 사용자가 이메일 확인시 -> 세션 값을 데이터베이스에 저장한다.
    /*
    2-1. 사용자가 이메일 확인 하면 호출.
    2-2. 세션 데이터를 데이터베이스에 저장한다.
     */
    @PostMapping("/signUp/email/auth")
    public void confirmAuthEmail() {

        // 1. 세션 데이터에서 id / email / password 값을 Dto화 한다.

        // 2. email / password 값을 암호화한다.

        // 3. 데이터베이스에 저장한다.

        // 4. 만약 저장을 실패할 시, 이메일로 실패 메시지를 발송한다.
    }
}
