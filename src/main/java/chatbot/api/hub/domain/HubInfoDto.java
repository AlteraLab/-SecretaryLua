package chatbot.api.hub.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class HubInfoDto {

    private Long hubId;

    private String externalIp;

    private int externalPort;

    private String internalIp;

    private int internalPort;
}
