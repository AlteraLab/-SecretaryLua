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
public class Hub implements Serializable {

    private int hubSeq;

//    private Long hubId;, 필요없는듯?? 그치??

    private String explicitIp;

    private int explicitPort;
}
