package chatbot.api.skillHub.domain;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class HubTableVo {

    // list 때문에 썻던걸로 기억함.
    // 디비 컬럼명과 일치

    private Long hub_sequence;

    private Long admin_sequence;

    private String hub_name;

    private int port_number;

    private String current_ip;

    private String before_ip;

    private Timestamp last_used_time;

    private Timestamp created_at;

    private Timestamp updated_at;

    private boolean state;
}
