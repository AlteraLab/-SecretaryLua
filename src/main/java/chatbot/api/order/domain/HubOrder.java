package chatbot.api.order.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HubOrder implements Serializable {

    private int hubSeq;

    private Long hubId;

    private String explicitIp;

    private int explicitPort;
}
