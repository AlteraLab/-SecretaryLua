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

    private String requesterId;  // == providerId

    private String content;

    private boolean logType;
}
