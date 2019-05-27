package chatbot.api.buildcode.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SelectedBtn implements Serializable {

    private int cmd_code;   // 전달할 명령 코드 번호

    private int data;       // 아무 의미 없는 코드, 시간 차이, 직접 입력
}
