package chatbot.api.skillHub.domain;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class HubInfoVo {

    // client 로 부터 받는 데이터들

    private Long hubSequence;

    private String hubName;

    private String externalIp;

    private int externalPort;

    private String internalIp;

    private int internalPort;

    private String beforeIp;

    private Timestamp lastUsedTime;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    private boolean state;
}
