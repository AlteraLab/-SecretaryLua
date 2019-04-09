package chatbot.api.order.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CmdOrderDto implements Serializable {

    private int cmdSeq;    // 사용자에게 보여줄 데이터

    private int cmdCode;   // 사용자에게 보여줄 데이터

    private String cmdName;// 사용자에게 보여줄 데이터

    private char cmdType;  // "d" == "동적"
                           // "s" == "정적"

    private int cmdId;     // 디비에 저장되는 시퀀스
                           // 사용자가 선택한 cmdType이 d 라면, 이것으로 dy_command_info 조회
}
