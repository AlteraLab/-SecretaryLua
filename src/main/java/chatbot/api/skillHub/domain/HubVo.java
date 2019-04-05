package chatbot.api.skillHub.domain;

import lombok.Data;

import java.sql.Timestamp;

@SuppressWarnings("ALL")
@Data
public class HubVo {

    // DB에서 가져올 데이터들

    private Long hub_sequence;

    private Long admin_sequence;

    private String hub_name;

    private String external_ip;

    private int external_port;

    private String internal_ip;

    private int internal_port;

    private String before_ip;

    private Timestamp last_used_time;

    private Timestamp created_at;

    private Timestamp updated_at;

    private boolean state;

    private String role;
}
