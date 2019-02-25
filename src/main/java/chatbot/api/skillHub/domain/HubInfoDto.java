package chatbot.api.skillHub.domain;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class HubInfoDto {

    private Long hubSeq;

    private Long adminSeq;

    private String hubName;

    private String externalIp;

    private int externalPort;

    private String internalIp;

    private int internalPort;

    private String beforeIp;

    private Date lastUsedTime;

/*    private int connectionModuleNum;

    private boolean commandExecutionStat;*/
}
