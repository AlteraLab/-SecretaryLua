package chatbot.api.hub.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserHubDto {

    private Long hubId;

    private Long userId;

    private String role;
}
