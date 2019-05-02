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
public class MainOrder implements Serializable {

    // hashKey
    private String hProviderId;

    private int currentParentCode;

    private HubOrder[] hubOrderList;

    private DevOrder[] devOrderList;

    private CmdOrder[] cmdOrderList;

    // IOT HUB 로 전달하는 데이터
    private SelectOrder selectOrder;
}
