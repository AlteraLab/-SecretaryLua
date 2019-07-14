package chatbot.api.textbox.domain.textboxdata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;
import java.io.Serializable;

import static chatbot.api.textbox.utils.TextBoxConstants.BUTTON_TYPE_CONTROL;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class BtnDTO implements Serializable {

    private Integer boxId;

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
    예약         `5` <- 없앨 예정
     */
}