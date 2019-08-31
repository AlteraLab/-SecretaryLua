package chatbot.api.developer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DvlpEventDTO {

    private Integer eventId;

    private Integer devId;

    private String dataKey;

    private String outputType;

    private String ruleType;

    private String ruleValue;

    private Integer priority;

    private DvlpNotifyBoxDTO notifyBoxDTO;

    private DvlpThirdServerDTO thirdServerDTO;

    private DvlpControlDTO controlDTO;
}
