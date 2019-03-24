package chatbot.api.role.domain;

import lombok.Data;

@Data
public class RoleVo {

    private Long hub_sequence;

    private Long user_sequence;

    private String role;
}
