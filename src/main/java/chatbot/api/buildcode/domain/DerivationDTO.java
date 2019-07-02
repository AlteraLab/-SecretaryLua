package chatbot.api.buildcode.domain;

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

    private Long derivationCode;    // PK

    private Long upperBoxId;        // box - derivation ( 1:1 ) 과 관계를 맺음

    private Long btnUppedBoxId;

    private Long timeUpperBoxId;

    private Long inputUpperBoxId;

    private Long eventCode;

    private Long lowerBoxId;        // box - derivation ( 1:N ) 과 관계를 맺음

    private String authKey;         // FK
}
