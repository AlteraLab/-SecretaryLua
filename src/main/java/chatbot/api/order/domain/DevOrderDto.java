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
public class DevOrderDto implements Serializable {

    private int devSeq;             // 사용자에게 보여줄 데이터

    private String devName;         // 사용자에게 보여줄 데이터

    private String devMacAddr;      // 장치의 맥주소

    private String devIdentifier;   // 장치 식별자, 하드웨어 개발자가 지정
                                    // ex) "SAMSUNG TV DS5"
                                    // commercial table에서 조회할때 쓰임
}
