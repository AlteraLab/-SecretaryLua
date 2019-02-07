package chatbot.api.skillHub.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SkillHubUserInfoDto {

    private Long hubSeq;

    private Long userSeq;

    private String role;
}
