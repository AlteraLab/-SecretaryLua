package chatbot.api.order.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SelectCmdOrder {

    private int cmdCode;   // 전달할 명령 코드 번호

    private char cmdType;  // 코드의 타입
                           /*
                           1. s : 정적 타입
                           2. d : 직접 입력 타입
                           3. b : 버튼 입력 타입
                           4. t : 시간 입력 타입
                            */

    private Object data;   // 버튼이 d, b, c 타입일시, 추가되는 데이터
}
