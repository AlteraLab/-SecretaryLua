package chatbot.api.common.config.rabbit.services.consumer.keepalive;

import chatbot.api.common.config.rabbit.RabbitMQConstants;
import chatbot.api.common.config.rabbit.domain.sub.KeepAliveMessage;
import chatbot.api.skillhub.repository.KeepAliveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KeepAliveMessageConsumer {

    @Autowired
    private KeepAliveRepository keepAliveRepository;

    @RabbitListener(queues = {RabbitMQConstants.SKILL_KEEP_ALIVE_QUEUE})
    public void consumeKeepAliveMessage(final KeepAliveMessage message) {
        log.info("=========== Consume Keep-Alive Message 시작 ===========");
        log.info("Mac -> " + message.getMac());
        if(keepAliveRepository.find(message.getMac()) != null) {
            log.info("데이터 갱신");
            keepAliveRepository.update(message.getMac(), true);
        } else {
            log.info("Redis 내에 데이터가 없습니다. 데이터를 추가합니다");
            keepAliveRepository.save(message.getMac(), true);
        }
        log.info("=========== Consume Keep-Alive Message 종료 ===========");
    }
}
