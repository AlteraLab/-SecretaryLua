package chatbot.api.skillHub.domain;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class HubInfoVo {

    private Long hubSequence;

    private String hubName;

    private String externalIp;

    private int externalPort;

    private String internalIp;

    private int internalPort;

    private String beforeIp;

    private Timestamp lastUsedTime;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    private boolean state;

    //    private Date lastUsedTime;
/*  private int connectionModuleNum;

    private boolean commandExecutionStat;  */
}
