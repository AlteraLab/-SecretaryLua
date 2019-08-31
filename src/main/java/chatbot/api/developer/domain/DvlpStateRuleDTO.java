package chatbot.api.developer.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DvlpStateRuleDTO {

    // res_box_crs

    private Integer modId;    // 모델 아이디 (PK)

    private Integer devId;    // 디바이스 아이디 (FK)

    private Integer modDevId; // 모델 디바이스 아이디 (FK, 데이터 모델)

    private Integer boxId;    // 박스 아이디 (FK)

    private String dataKey;   // 데이터 키 (FK, 데이터 모델)

    // 허브에게 조회한 값, ruleType, ruleValue, mapVal이 하나의 규칙 쌍
    /*
    ex)
    조회 박스에 #{temp} 라는 문구가 존재하고, "temp"(dataKey 문자열), 32(조회 값),
    "4"(규칙 타입), "20"(규칙 값), "덥다"(사상 값) 인 경우, 32 > 20이 성립하므로
    "덥다"라는 사상 값을 #{temp}에게 치환하여야 함.
     */
    private String ruleType;  // 규칙 연산 타입
    /*
    "1" - 규칙 없음으로 적용, 허브로 부터 조회 한 값을 그대로 적용
    "2" - "=="
    "3" - "!="
    "4" - ">"
    "5" = "<"
     */
    private String ruleValue; // 규칙 적용 값
    /*
    ruleType 과 함께 사용되는 값
    String 타입 이므로 dataKey 에 대응되는 dataType 으로 parse 후에
    ruleType 에 일치하는 연산자와 비교해야 할 것
     */

    private String mapVal;    // 치환 문자열
    /*
    허브로 부터 조회 한 값,
    ruleType, ruleValue 규칙에 부합한다면 mapVal로 치환
     */

    private Integer priority; // 규칙 우선순위
}
