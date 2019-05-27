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
public class BoxDto implements Serializable {

    private Long boxId;  // PK

    private String authKey;  // VARCHAR 32

    private String preText;  // VARCHAR 50

    private String postText; // VARCHAR 50

    private int boxType;
}
