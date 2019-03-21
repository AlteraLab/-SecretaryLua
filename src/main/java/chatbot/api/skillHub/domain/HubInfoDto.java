package chatbot.api.skillHub.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class HubInfoDto {

    private Long hubSeq;

    private Long adminSeq;

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

    //private Date lastUsedTime;
/*    private int connectionModuleNum;

    private boolean commandExecutionStat;*/
}
