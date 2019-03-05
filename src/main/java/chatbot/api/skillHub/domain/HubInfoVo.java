package chatbot.api.skillHub.domain;

import lombok.Data;

import java.util.Date;

@Data
public class HubInfoVo {

    private String hubName;

    private String externalIp;

    private int externalPort;

    private String internalIp;

    private int internalPort;

    private String beforeIp;

    private Date lastUsedTime;

/*  private int connectionModuleNum;

    private boolean commandExecutionStat;  */
}
