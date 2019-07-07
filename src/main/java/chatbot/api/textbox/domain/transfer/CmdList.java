package chatbot.api.textbox.domain.transfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CmdList implements Serializable {

    private Integer btnType;
    /*
    제어 1
    조회-허브-예약 2
    조회-허브-센싱 3
    조회-디바이스 4
    예약 5
     */
    private Integer eventCode;

    private ArrayList<Additional> additional;
}
