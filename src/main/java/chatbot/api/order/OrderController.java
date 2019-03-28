package chatbot.api.order;

import chatbot.api.common.domain.ResponseDto;
import chatbot.api.common.domain.kakao.openbuilder.RequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class OrderController {

    @PostMapping("/module/type")
    public ResponseDto callModuleType(@RequestBody RequestDto requestDto) {
        log.info(requestDto.toString());
        return ResponseDto.builder()
                .msg("ModuleType")
                .build();
    }

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