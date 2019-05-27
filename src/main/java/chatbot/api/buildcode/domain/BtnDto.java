package chatbot.api.buildcode.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BtnDto implements Serializable {

    private int btnSeq;     // DB 에 저장되는 데이터 아님.

    private String authKey; // FK

    private Long boxId;

    private int eventCode;

    private String btnName;  // VARCHAR 16

    private String blockId;  // VARCHAR 24
}
