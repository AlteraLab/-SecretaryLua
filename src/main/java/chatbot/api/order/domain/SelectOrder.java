package chatbot.api.order.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SelectOrder implements Serializable {

    // 전달 경로
    private String externalIp;

    private int externalPort;

    private String devMacAddr;


    private SelectCmdOrder[] cmds; // 전달하는 데이터들

    private int curIndex;
}
