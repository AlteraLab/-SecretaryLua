package chatbot.api.hublog.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HubLogDTO {

    private Timestamp recordedAt;

    private Long hubId;

    private Integer hrdwrId;        // 0705 추가

    private String hrdwrName;      // 0705 추가

    private String requesterId;  // == providerId

    private String content;

    private boolean logType;
}
