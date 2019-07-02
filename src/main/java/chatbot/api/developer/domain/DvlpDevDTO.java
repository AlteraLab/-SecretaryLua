package chatbot.api.developer.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DvlpDevDTO {

    private Long devId;

    private String authKey;

    private String devName;

    private String devDefName;

    private String devType;

    private String category;
}
