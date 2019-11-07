package chatbot.api.dev.domain;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EventVO {

    private Integer eventId;

    private Integer priority;

    private String dataKey;

    private String outputType;

    private String ruleType;

    private String ruleValue;

    private String preText;

    private String postText;

    private String host;

    private String port;

    private String path;

    private Integer evCode;

    private String authKey;


}
