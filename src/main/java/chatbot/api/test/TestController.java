package chatbot.api.test;

import chatbot.api.common.RequestDto;
import chatbot.api.common.domain.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

    @PostMapping("/test/1")
    public ResponseDto test_step1(@RequestBody RequestDto requestDto) {
      //  System.out.println("User >> " + requestDto.getUserRequest().getUser());
      //  System.out.println("Params >> " + requestDto.getUserRequest().getParams());
        log.info("step1");
        log.info(requestDto.getUserRequest().getUtterance());
        return ResponseDto.builder()
                .status(HttpStatus.OK)
                .msg("Hello World!!")
                .data(null)
                .build();
    }

    @PostMapping("/test/2")
    public ResponseDto test_step2(@RequestBody RequestDto requestDto) {
        log.info("step2");
        log.info(requestDto.getUserRequest().getUtterance());
        return ResponseDto.builder()
                .status(HttpStatus.OK)
                .msg("Hello World!!")
                .data(null)
                .build();
    }

    @PostMapping("/test/3")
    public ResponseDto test_step3(@RequestBody RequestDto requestDto) {
        log.info("step3");
        log.info(requestDto.getUserRequest().getUtterance());
        return ResponseDto.builder()
                .status(HttpStatus.OK)
                .msg("Hello World!!")
                .data(null)
                .build();
    }

    @PostMapping("/test/4")
    public ResponseDto test_step4(@RequestBody RequestDto requestDto) {
        log.info("step4");
        log.info(requestDto.getUserRequest().getUtterance());
        return ResponseDto.builder()
                .status(HttpStatus.OK)
                .msg("Hello World!!")
                .data(null)
                .build();
    }
}



      /* System.out.println(requestDto.getUserRequest().getUtterance());
        // 1. 문자열 파싱, ex) 7 "31 예약 2시간" -> 7 / 31 / "예약 2시간"
        // 1-1. 문자열 유효성 체크, 문자열 입력할때 "번" 이랑 숫자는
        // 1-2. 문자열 파싱3

        String utterance = requestDto.getUserRequest().getUtterance();

        int index1 = utterance.indexOf("번");
        String strHubSeq = utterance.substring(0, index1).replaceAll("[^0-9]", "");
        System.out.println("strHubSeq >>>" + strHubSeq);

        int index2 = utterance.indexOf("번", (index1 + 1));
        String strModuleSeq = utterance.substring(index1, index2).replaceAll("[^0-9]", "");
        System.out.println("strModuleSeq >>> " + strModuleSeq);


*/
// 2. 허브 테이블에서 7번 시퀀스 레코드의 externalIp / Port를 get한다. 만약에 없으면 "해당 허브는 존재하지 않습니다." 반환


// 3. hub_module 테이블과 hub_feature 테이블을 Join 해서 hub_module_sequence = 31 / hub_feature_name = "예약 2시간" 이 있는지 검사. 없으면 "해당 모듈이 없거나 기능이 존재하지 않습니다." 반환


// 4. 있으면 restTemplate를 이용하여 7번 허브 서버에 구현된 API를 호출하여 (31 / 예약 2시간) 데이터를 전송한다.


        /*  System.out.println(requestDto.getUserRequest());
        // 테스트 완료
        // 내일부터 진행하면 될듯....
        // PostMapping으로 ...
        // 딱히 어려운 기능 안쓰고 말이다!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        String test = "이상원\n안교준\n김진혁\n엄선용\n김병창";
        */