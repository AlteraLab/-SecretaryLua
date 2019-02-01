package chatbot.api.hub.domain;

import lombok.Data;

@Data
public class HubInfoVo {

    private String externalIp;

    private int externalPort;

    private String internalIp;

    private int internalPort;
}
