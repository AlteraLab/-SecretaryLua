package chatbot.api.skillHub.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SkillHubInfoDto {

    private Long hubSeq;

    private Long adminSeq;

    private String hubName;

    private String currentIp;        // externalIp와 맵핑

    private String beforeIp;

    private int port;

    private int connectionModuleNum;

    private boolean commandExecutionStat;
}
