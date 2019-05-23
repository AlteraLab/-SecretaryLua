package chatbot.api.skillhub.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class HubInfoDto {

    private Long hubId;

    private Long adminId;

    private String hubName;

    private String hubDescript;

    private String hubSearchId;

    private String macAddr;

    private String externalIp;

    private int externalPort;

    private String internalIp;

    private int internalPort;

    private String beforeIp;

    private Timestamp lastUsedTime;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    private boolean state;
}
