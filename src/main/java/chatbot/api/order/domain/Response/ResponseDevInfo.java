package chatbot.api.order.domain.Response;

import chatbot.api.order.domain.DevOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDevInfo {

    private boolean status;

    private DevOrder[] devInfo;
}
