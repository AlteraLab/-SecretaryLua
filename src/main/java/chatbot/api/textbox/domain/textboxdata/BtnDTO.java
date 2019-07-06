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
public class BtnDTO implements Serializable {

    private Integer btnCode;

    private Integer eventCode;

    private String btnName;

    private Integer idx;

    private boolean isSpread;

    private Character btnType;
    /*
    제어         `1`
    조회-허브-예약 `2`
    조회-허브-센싱 `3`
    조회-디바이스  `4`
    예약         `5`
     */
}