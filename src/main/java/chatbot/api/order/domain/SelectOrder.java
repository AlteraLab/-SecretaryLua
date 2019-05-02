package chatbot.api.order.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SelectOrder {

    // 전달 경로
    private String externalIp;

    private int externalPort;

    private String devMacAddr;


    // 전달하는 데이터들
    private SelectCmdOrder[] cmds;
}
