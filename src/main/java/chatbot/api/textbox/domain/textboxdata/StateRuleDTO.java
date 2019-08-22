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
public class StateRuleDTO implements Serializable {

    // 결과 박스 데이터 모델

    private Integer rulePriority;

    private Character ruleType;

    private String ruleValue;

    private String mapVal;

    private String dataKey;    // (FK, 데이터 모델)

    private Integer modId;     // (FK, 데이터 모델)

    private Integer boxId;     // (FK, 박스)

    private Integer modDevId;  // PK
}
