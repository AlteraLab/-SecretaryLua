package chatbot.api.textbox.domain.textboxdata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DerivationDTO implements Serializable {

    private Integer upperBoxId;        // box - derivation ( 1:1 ) 과 관계를 맺음

    private Integer btnCode;

    private Integer lowerBoxId;        // box - derivation ( 1:N ) 과 관계를 맺음
}
