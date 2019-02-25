package chatbot.api.skillHub.domain;

import lombok.Data;

import java.util.Date;

@Data
public class HubTableVo {

    // 디비 컬럼명과 일치

    private Long hub_sequence;

    private Long admin_sequence;

    private String hub_name;

    private int port_number;

    private String current_ip;

    private String before_ip;

    private Date last_used_time;

/*  private boolean command_execution_statement;

    private int connection_module_num;*/
}
