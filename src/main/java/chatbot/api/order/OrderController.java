package chatbot.api.order;

import chatbot.api.common.domain.ResponseDto;
import chatbot.api.common.domain.kakao.openbuilder.RequestDto;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseDtoVerTwo;
import chatbot.api.order.services.OrderResponseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class OrderController {

    @Autowired
    private OrderResponseService orderResponseService;


    @PostMapping("/module/type")
    public ResponseDtoVerTwo callModuleType(@RequestBody RequestDto requestDto) {

        log.info(requestDto.toString());
        String providerId = requestDto.getUserRequest().getUser().getProperties().getAppUserId();
        String devCategory = requestDto.getUserRequest().getUtterance();
        return orderResponseService.responserHubsAboutDevType(providerId, devCategory);

    }



    @PostMapping("/hub/id")
    public ResponseDtoVerTwo callHubId(@RequestBody RequestDto requestDto) {

        // In-Memory에 사용자의 appUserId (provider Id)가 없다면, "명령 절차를 준수하세요!" 라는 메시지 전송
        String prividerId = requestDto.getUserRequest().getUser().getProperties().getAppUserId();
        String hubSequence = requestDto.getUserRequest().getUtterance().replaceAll("[^0-9]", "");
        int hubSeq = Integer.parseInt(hubSequence);
        System.out.println("hubSeq : " + hubSeq);
        log.info(requestDto.toString());
        return orderResponseService.responserDevsAboutSelectedHub(prividerId, hubSeq);
    }

    ///////////////////////////////////////


    @PostMapping("/module/id")
    public ResponseDto callModuleId(@RequestBody RequestDto requestDto) {

        // In-Memory에 사용자의 appUserId (provider Id)가 없다면, "명령 절차를 준수하세요!" 라는 메시지 전송
        String prividerId = requestDto.getUserRequest().getUser().getProperties().getAppUserId();
        String hubId = requestDto.getUserRequest().getUtterance();
        log.info(requestDto.toString());
        return ResponseDto.builder()
                .msg("ModuleId")
                .build();
    }



    @PostMapping("/command/code")
    public ResponseDto callCommandCode(@RequestBody RequestDto requestDto) {

        // In-Memory에 사용자의 appUserId (provider Id)가 없다면, "명령 절차를 준수하세요!" 라는 메시지 전송
        String prividerId = requestDto.getUserRequest().getUser().getProperties().getAppUserId();
        String hubId = requestDto.getUserRequest().getUtterance();
        log.info(requestDto.toString());
        return ResponseDto.builder()
                .msg("CommandCode")
                .build();
    }



    @PostMapping("/command/type")
    public ResponseDto callCommandType(@RequestBody RequestDto requestDto) {

        // In-Memory에 사용자의 appUserId (provider Id)가 없다면, "명령 절차를 준수하세요!" 라는 메시지 전송
        String prividerId = requestDto.getUserRequest().getUser().getProperties().getAppUserId();
        String hubId = requestDto.getUserRequest().getUtterance();
        log.info(requestDto.toString());
        return ResponseDto.builder()
                .msg("CommandType")
                .build();
    }
}