package chatbot.api.common.config.rabbit.services.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class testSender {

    private final RabbitTemplate rabbitTemplate;

    public testSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /*@Scheduled(fixedDelay = 3000L)
    public void sendKeepAliveMsg() {
        KeepAliveMessage keepAliveMessage = new KeepAliveMessage("b8:27:eb:96:e5:b4");
        log.info("Keep-Alive Msg -> " + keepAliveMessage);
        rabbitTemplate.convertAndSend(RabbitMQConstants.SKILL_EXCHANGE, RabbitMQConstants.SKILL_KEEP_ALIVE_ROUTE_KEY, keepAliveMessage);
    }*/

/*    @Scheduled(fixedDelay = 5000L)
    public void sendEstablishMessage() {
        EstablishMessage establishMessage = new EstablishMessage("203.250.32.29", 54322, "b8:27:eb:96:e5:b4");
        log.info("Establish Msg -> " + establishMessage);
        rabbitTemplate.convertAndSend(RabbitMQConstants.SKILL_EXCHANGE, RabbitMQConstants.SKILL_ESTABLISH_ROUTE_KEY, establishMessage);
    }*/
}
