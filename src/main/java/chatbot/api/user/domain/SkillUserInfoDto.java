package chatbot.api.user.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SkillUserInfoDto {

    private Long userSeq;

    private String userId;

    private String email;

    private boolean emailValidStat;

    private boolean emailVerifiedStat;

    private int usingHubNum;

    private int managementHubNum;
}
