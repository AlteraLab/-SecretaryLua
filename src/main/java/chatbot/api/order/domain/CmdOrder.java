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
public class CmdOrder implements Serializable {

    private String description; // 사용자에게 보여줄 데이터

    private String cmdName;     // 사용자에게 보여줄 데이터


    private int cmdCode;

    private int parentCode;

    private String blockId;


    // blockId가 버튼 블록 id 라면,
    private String btnList;

    private String btnTitle;

    private int [] btnSeq;      // btnList 로 구함

    private String [] btnNames; // btnList 로 구함


    // blockId가 직접 입력 블록 id 라면,
    private String directTitle;

    private int inputEx;
}