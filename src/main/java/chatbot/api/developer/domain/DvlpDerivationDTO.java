package chatbot.api.developer.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DvlpDerivationDTO {

    private Integer btnCode;

    @JsonAlias("pboxId")
    private Integer upperBoxId;  // 부모 - 상위

    @JsonAlias("cboxId")
    private Integer lowerBoxId;  // 자식 - 하위
}
