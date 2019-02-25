package chatbot.api.skillHub.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HubUserInfoDto {

    private Long hubSeq;

    private Long userSeq;

    private String role;
}
