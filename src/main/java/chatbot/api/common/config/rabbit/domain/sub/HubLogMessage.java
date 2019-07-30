package chatbot.api.common.config.rabbit.domain.sub;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@ToString
@Slf4j
public class HubLogMessage {

    private Timestamp recordedAt;

    private Long hubId;

    private Integer hrdwrId;

    private String hrdwrName;

    private String requesterId;  // == providerId

    private String content;

    private boolean logType;


    public HubLogMessage() {
    }

    public HubLogMessage(@JsonAlias("hubId") Long hubId,
                         @JsonAlias("hrdwrId") Integer hrdwrId,
                         @JsonAlias("hrdwrName") String hrdwrName,
                         @JsonAlias("requesterId") String requesterId,
                         @JsonAlias("content") String content,
                         @JsonAlias("logType") boolean logType) {

        log.info("초기화");
        this.recordedAt = Timestamp.valueOf(LocalDateTime.now());
        this.hubId = hubId;
        this.hrdwrId = hrdwrId;
        this.hrdwrName = hrdwrName;
        this.requesterId = requesterId;
        this.content = content;
        this.logType = logType;
        log.info("This.toString" + this.toString());
    }


    public void setRecordedAt(Timestamp recordedAt) {
        this.recordedAt = recordedAt;
    }

    public Timestamp getRecordedAt() {
        return recordedAt;
    }

    public Long getHubId() {
        return hubId;
    }

    public Integer getHrdwrId() {
        return hrdwrId;
    }

    public String getHrdwrName() {
        return hrdwrName;
    }

    public String getRequesterId() {
        return requesterId;
    }

    public String getContent() {
        return content;
    }

    public boolean isLogType() {
        return logType;
    }
}
