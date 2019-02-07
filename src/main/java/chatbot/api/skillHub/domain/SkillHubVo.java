package chatbot.api.skillHub.domain;

import lombok.Data;

@Data
public class SkillHubVo {

    private String externalIp;

    private int port;

    private Long userId;
}
