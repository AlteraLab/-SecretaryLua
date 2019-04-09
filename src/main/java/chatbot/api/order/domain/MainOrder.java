package chatbot.api.order.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MainOrder implements Serializable {

    // hashKey
    private String providerId;

    // step 1 때 저장하는 데이터
    private String devCategory;      // 모듈 목록을 조회할 때 쓰임
    private ArrayList<HubOrderDto> hubOrderList;
    /*
    private int hubSeq;              // 디비에서 데이터를 뽑아왔을때 지정하는 시퀀스
                                     // 허브 목록을 보여줄 때 사용자에게 보여줄 데이터
    private Long hubId;              // 디비에 저장된 허브 정보의 시퀀스
    private String hubName;          // 허브 목록을 보여줄 때 사용자에게 보여줄 데이터
    private String explicitIp;       // 데이터를 보낼 경로
    private int explicitPort;        // 데이터를 보낼 경로
    */

    // step 2 때 저장하는 데이터
    private ArrayList<DevOrderDto> devOrderList;
    /*
    private int devSeq;             // 사용자에게 보여줄 데이터
    private String devName;         // 사용자에게 보여줄 데이터
    private String devMacAddr;      // 장치의 맥주소
    private String devIdentifier;   // 장치 식별자, 하드웨어 개발자가 지정
                                    // ex) "SAMSUNG TV DS5"
                                    // commercial table에서 조회할때 쓰임
     */

    // step 3 때 저장하는 데이터
    private ArrayList<CmdOrderDto> cmdOrderList;
    /*
    private int cmdSeq;    // 사용자에게 보여줄 데이터
    private int cmdCode;   // 사용자에게 보여줄 데이터
    private String cmdName;// 사용자에게 보여줄 데이터
    private char cmdType;  // "d" == "동적"
                           // "s" == "정적"
    private int cmdId;     // 디비에 저장되는 시퀀스
                           // 사용자가 선택한 cmdType이 d 라면,
                           // 이것으로 dy_command_info 조회
                           // s 라면 그냥 사용자에게 전송
     */


    // IOT HUB 로 전달하는 데이터
    private SelectOrderVo selectOrderVo;
}
