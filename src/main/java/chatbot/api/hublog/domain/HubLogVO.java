package chatbot.api.hublog.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Collections;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HubLogVO {

    private Timestamp recordedAt;

    private String requesterName;

    private String content;

    private boolean logType;
}
