package chatbot.api.developer.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DvlpBoxDTO {

    private Integer boxId;

    private String preText;

    private String postText;

    private Integer boxType;
    /*
    boxType : 박스의 종류를 식별하는데 사용하는 int 값
    Entry : 5
    버튼   : 1
    동적   : 2
    시간   : 3
     */
}
