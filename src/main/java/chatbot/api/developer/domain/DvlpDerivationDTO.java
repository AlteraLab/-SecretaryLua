package chatbot.api.developer.domain;

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

    private Integer pboxId;  // 부모 - 상위

    private Integer cboxId;  // 자식 - 하위
}
