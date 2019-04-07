package chatbot.api.order.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class SelectOrderVo implements Serializable {

    private String providerId;

    private String devCategory;   // 디비에서 정보를 조회할 때 사용

    private Long hubId;         // 디비에서 정보를 조회할 때 사용

    private String externalIp;    // 데이터를 전송할 허브 경로

    private int externalPort;  // 데이터를 전송할 허브 경로


    private String devMacAddr;    // 디바이스 고유 번호

    private String cmdCode;       // 명령 코드 번호

    private String cmdType;       // Dynamic or Static

    private String dynamicInput;  // Dynamic Input
}
