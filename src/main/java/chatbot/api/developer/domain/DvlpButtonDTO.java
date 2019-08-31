package chatbot.api.developer.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
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

    @JsonAlias("eventCode")
    private Integer evCode;

    private Boolean isSpread;

    @JsonAlias("type")
    private Character btnType;
    /*
    버튼타입     값
    제어 : 1
     */
}
