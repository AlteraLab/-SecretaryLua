package chatbot.api.common.config.rabbit.services.consumer;

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
    public void consumeKeepAliveMessage(final KeepAliveMessage keepAliveMessage) {
        log.info("=========== Consume Keep-Alive Message 시작 ===========");
        log.info("Mac -> " + keepAliveMessage.getMac());
        if(keepAliveRepository.find(keepAliveMessage.getMac()) != null) {
            keepAliveRepository.update(keepAliveMessage.getMac(), true);
        } else {
            keepAliveRepository.save(keepAliveMessage.getMac(), true);
        }
        log.info("=========== Consume Keep-Alive Message 종료 ===========");
    }
}
