package chatbot.api.developer.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DvlpButtonDTO {

    private Integer btnCode;

    private String btnName;

    private Integer idx;

    private Integer boxId;

    private Integer evCode;

    private Boolean isSpread;

    private Character btnType;
    /*
    버튼타입     값
    제어 : 1
     */
}
