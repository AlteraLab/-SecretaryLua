package chatbot.api.developer.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DvlpDataModelDTO {

    // data model

    private String dataKey; // 하나의 디바이스내에서는 유일한 상태 키 값

    private Integer devId;

    private String dataType; // 해당 데이터 키에 대응하는 타입
    /*
    "1" = byte
    "2" = integer
    "3" = long
    "4" = double
    "5" = string
    "6" = char
     */

    private String modType;  // 데이터 모델의 종류
    /*
    "0" - 디바이스 조회에 사용되는 데이터 모델
    "1" - 센싱-조회에 사용되는 데이터 모델
     */

    private Boolean isEv;
    /*
    해당 데이터 모델에 이벤트를 적용시킬 수 있는지
    false로 설정한 데이터 모델은 이벤트를 연결하는 것이 불가능
     */
}
