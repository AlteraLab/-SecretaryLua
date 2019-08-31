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
public class DataModelDTO implements Serializable {

    private String dataKey;

    private Boolean isEv;

    private String dataType; // 데이터 타입
    /*
    '1' - byte
    '2' - integer
    '3' - long
    '4' - double
    '5' - string
    '6' - char
     */

    private Character modType;  // 모델 타입
    /*
    '0' - 디바이스-조회에 사용되는 데이터 모델
    '1' - 센싱-조회에 사용되는 데이터 모델
     */
}
