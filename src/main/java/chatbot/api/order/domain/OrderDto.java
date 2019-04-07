package chatbot.api.order.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.print.DocFlavor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {

    private String devCategory;    // 1. 모듈 목록을 조회할 때 쓰임

    private String hubId;          // 1. 허브 목록을 보여줄 때 사용자에게 보여줄 데이터
                                   // 2. 모듈 목록을 조회할 때 쓰임

    private String hubName;        // 1. 허브 목록을 보여줄 때 사용자에게 보여줄 데이터

    private String explicitIp;     // 1. 데이터를 보낼 경로

    private String explicitPort;   // 1. 데이터를 보낼 경로


    private String devMacAddr;     // 1. 데이터를 보낼 허브에 전송해줄 데이터

    private String devId;          // 1. 디바이스 목록을 보여줄 때 사용자에게 보여주는 데이터
                                   // 2. 사용자가 선택했을때 이 데이터를 이용해서 선택한 디바이스를 찾는다.

    private String devName;        // 1. 디바이스 목록을 보여줄 때 사용자에게 보여주는 데이터

    private String devDescript;    // 1. 디바이스 목록을 보여줄 때 사용자에게 보여주는 데이터


    private String cmdName;        //

    private String cmdCode;        //

    private String cmdType;        //

    private String selectCode;     //
}
