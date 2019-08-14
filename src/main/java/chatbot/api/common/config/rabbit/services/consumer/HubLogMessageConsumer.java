package chatbot.api.common.config.rabbit.services.consumer;

import chatbot.api.common.config.rabbit.RabbitMQConstants;
import chatbot.api.common.config.rabbit.domain.sub.HubLogMessage;
import chatbot.api.mappers.HubLogMapper;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static chatbot.api.hublog.utils.HubLogConstatns.EXCEPTION_MSG_FAILED_RECORD_HUB_LOG;

@Service
@Slf4j
public class HubLogMessageConsumer {

    @Autowired
    private HubLogMapper hubLogMapper;


    @RabbitListener(queues = {RabbitMQConstants.SKILL_HUB_LOG_QUEUE})
    public void consumeHubLogMessage(final HubLogMessage hubLogMessage) {
        log.info("=========== Consume Hub Log Message 시작 ===========");
        log.info("Hub Log Queue 에 데이터가 들어왔습니다.");
        hubLogMessage.setRecordedAt(Timestamp.valueOf(LocalDateTime.now()));
        log.info("HubLogMessage : " + hubLogMessage.toString());
        try {
            hubLogMapper.saveHubLog(hubLogMessage);
        } catch (Exception e) {
            log.info(EXCEPTION_MSG_FAILED_RECORD_HUB_LOG);
            e.printStackTrace();
        }
        log.info("=========== Consume Establish Message 종료 ===========");
    }
}
