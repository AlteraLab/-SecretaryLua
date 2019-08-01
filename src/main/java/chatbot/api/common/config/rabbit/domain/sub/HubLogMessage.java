package chatbot.api.common.config.rabbit.domain.sub;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@ToString
@Slf4j
public class HubLogMessage {

    private Timestamp recordedAt;

    private String hubMac; // 추가

    private String devType; // 추가

    private String requesterId;  // == providerId

    private String content;

    private String devMac; // 추가

    private boolean logType;


    public HubLogMessage() {
    }

    public HubLogMessage(@JsonAlias("hubMac") String hubMac,
                         @JsonAlias("devType") String devType,
                         @JsonAlias("devMac") String devMac,
                         @JsonAlias("requesterId") String requesterId,
                         @JsonAlias("content") String content,
                         @JsonAlias("logType") boolean logType) {

        log.info("초기화");
        this.recordedAt = Timestamp.valueOf(LocalDateTime.now());
        this.hubMac = hubMac;
        this.devType = devType;
        this.requesterId = requesterId;
        this.content = content;
        this.logType = logType;
        this.devMac = devMac;

        log.info("This.toString" + this.toString());
    }


    public void setRecordedAt(Timestamp recordedAt) {
        this.recordedAt = recordedAt;
    }

    public Timestamp getRecordedAt() {
        return recordedAt;
    }

    public String getDevMac() {
        return devMac;
    }

    public String getHubMac() {
        return hubMac;
    }

    public String getDevType() {
        return devType;
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
