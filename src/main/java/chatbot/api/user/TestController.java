package chatbot.api.user;


import chatbot.api.common.domain.ResponseDto;
import chatbot.api.common.domain.kakao.openbuilder.RequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static chatbot.api.user.utils.UserConstants.SUCCESS_MSG_TOKEN;

@RestController
@Slf4j
public class TestController {


    @PostMapping(value = "/test")
    public ResponseDto test(@RequestBody RequestDto requestDto) {
        System.out.println(requestDto);
        return ResponseDto.builder()
                .msg(SUCCESS_MSG_TOKEN)
                .status(HttpStatus.OK)
                .build();
    }

    @PostMapping(value = "/login")
    public ResponseDto userLogin(@RequestBody RequestDto requestDto) {
        System.out.println(requestDto);
        return ResponseDto.builder()
                .msg(SUCCESS_MSG_TOKEN)
                .status(HttpStatus.OK)
                .build();
    }
}