package chatbot.api.skillhub.domain;

import chatbot.api.buildcode.domain.Build;
import chatbot.api.buildcode.domain.Path;
import chatbot.api.buildcode.repository.BuildRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.print.DocFlavor;
import java.sql.Timestamp;
import java.util.ArrayList;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class HubInfoDTO {

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
