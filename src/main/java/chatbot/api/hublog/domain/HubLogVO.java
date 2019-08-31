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
public class HubLogVO {

    private Timestamp recordedAt;

    private Integer hrdwrId;

    private String hrdwrName;

    private String requesterName;

    private String content;

    private boolean logType;
}
