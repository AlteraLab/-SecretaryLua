package chatbot.api.order.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SelectOrderVo {

    // 전달 경로
    private String externalIp;

    private int externalPort;


    // 전달하는 데이터들
    private String devMacAddr;

    private int cmdCode;

    private char cmdType;


    // step 4 때 저장하는 데이터, (만약 사용자가 선택한 명령이 동적 명령이라면)
    private int dyCmdInput;

    private String dyCmdQuestion;   // 해당 동적 명령이 사용자에게 보여줄 질문

    private String dyCmdInputExam;  // 해당 동적 명령의 입력 예
}
