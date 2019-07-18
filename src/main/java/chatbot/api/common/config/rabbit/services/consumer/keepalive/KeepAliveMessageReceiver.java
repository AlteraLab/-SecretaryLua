package chatbot.api.common.config.rabbit.services.consumer.keepalive;

import chatbot.api.common.config.rabbit.RabbitMQConstants;
import chatbot.api.common.config.rabbit.domain.sub.KeepAliveMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KeepAliveMessageReceiver {

    // receiver : 메시지를 받아서, exchange 로 보낸 후, queue 에 넣음

    private final RabbitTemplate rabbitTemplate;

    public KeepAliveMessageReceiver(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    // 메시지를 받아서 exchange -> queue 에 보냄
    public void sendKeepAliveMessageToExchange(KeepAliveMessage keepAliveMessage) {
        log.info("Receiver 가 Keep-Alive Message 를 SKILL_EXCHANGE 로 보냈습니다.");
        log.info("keep-Alive Message -> " + keepAliveMessage.toString());
        rabbitTemplate.convertAndSend(RabbitMQConstants.SKILL_EXCHANGE, RabbitMQConstants.SKILL_KEEP_ALIVE_ROUTE_KEY, keepAliveMessage);
    }
}
