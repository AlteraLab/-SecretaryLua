package chatbot.api.textbox.domain.textboxdata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoxDTO implements Serializable {

    private Integer boxId;

    private String preText;

    private String postText;

    private Integer boxType;
    /*
    entry 5
    버튼   1
    동적   2
    시간   3
     */
}
