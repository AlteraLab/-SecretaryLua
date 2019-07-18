package chatbot.api.common.config.rabbit.services.consumer.establish;

import chatbot.api.common.config.rabbit.RabbitMQConstants;
import chatbot.api.common.config.rabbit.domain.sub.EstablishMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EstablishMessageReceiver {

    // receiver : 메시지를 받아서, exchange 로 보낸 후, queue 에 넣음

    private final RabbitTemplate rabbitTemplate;

    public EstablishMessageReceiver(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    // 메시지를 받아서 exchange -> queue 에 보냄
    public void sendEstablishMessageToExchange(EstablishMessage establishMessage) {
        log.info("Receiver 가 Establishment Message 를 SKILL_EXCHANGE 로 보냈습니다.");
        log.info("Establish Message -> " + establishMessage.toString());
        rabbitTemplate.convertAndSend(RabbitMQConstants.SKILL_EXCHANGE, RabbitMQConstants.SKILL_ESTABLISH_ROUTE_KEY, establishMessage);
    }
}