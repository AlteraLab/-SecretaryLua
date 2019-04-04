package chatbot.api.order;

import chatbot.api.common.domain.ResponseDto;
import chatbot.api.common.domain.kakao.openbuilder.RequestDto;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseDtoVerTwo;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseJsonFormat.Component.simpleText.SimpleText;
import chatbot.api.response.services.RequestJoinResponser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class OrderController {




    @PostMapping("/module/type")
    public ResponseDto callModuleType(@RequestBody RequestDto requestDto) {

        log.info(requestDto.toString());
        /*
        // 여기에 데이터베이스에 botUserKey를 기반으로 한 ID 값이 있는지 체크.
        if(requestDto.getUserRequest().getUser().getId() != null) {
            return ResponseDto.builder()
                    .title("등록되지 않은 사용자 입니다.")
                    .msg("\"등록\" 을 입력하세요.")
                    .status(HttpStatus.OK)
                    .build();
        }
         */

        return ResponseDto.builder()
                .title("사용 가능한 허브 목록 입니다.")
                .msg("test")
                .build();
    }

/*
    @PostMapping("/module/type")
    public ResponseDtoVerTwo callModuleType(@RequestBody RequestDto requestDto) {

        SimpleText simpleTextVo = new SimpleText();
        simpleTextVo.setText("");
//        return requestJoinResponser.responser(requestDto.getUserRequest().getUser().getId());
    }
*/


    @PostMapping("/hub/id")
    public ResponseDto callHubId(@RequestBody RequestDto requestDto) {
        log.info(requestDto.toString());
        return ResponseDto.builder()
                .msg("HubId")
                .build();
    }

    @PostMapping("/module/id")
    public ResponseDto callModuleId(@RequestBody RequestDto requestDto) {
        log.info(requestDto.toString());
        return ResponseDto.builder()
                .msg("ModuleId")
                .build();
    }

    @PostMapping("/command/code")
    public ResponseDto callCommandCode(@RequestBody RequestDto requestDto) {
        log.info(requestDto.toString());
        return ResponseDto.builder()
                .msg("CommandCode")
                .build();
    }

    @PostMapping("/command/type")
    public ResponseDto callCommandType(@RequestBody RequestDto requestDto) {
        log.info(requestDto.toString());
        return ResponseDto.builder()
                .msg("CommandType")
                .build();
    }
}