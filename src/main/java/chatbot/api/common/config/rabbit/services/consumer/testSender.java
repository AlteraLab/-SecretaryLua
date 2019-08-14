package chatbot.api.common.config.rabbit.services.consumer;

import chatbot.api.common.config.rabbit.RabbitMQConstants;
import chatbot.api.common.config.rabbit.domain.sub.EstablishMessage;
import chatbot.api.common.config.rabbit.domain.sub.HubLogMessage;
import chatbot.api.common.config.rabbit.domain.sub.KeepAliveMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Slf4j
public class testSender {

    private final RabbitTemplate rabbitTemplate;

    private Random random;

    public testSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

/*    @Scheduled(fixedDelay = 5000L)
    public void sendHubLogMessage() {
        HubLogMessage hubLogMessage = new HubLogMessage(
                "02:42:c0:24:d3:08",
                "3792813073514d2ba8b56a1c61b22d59",
                "11:11:11:11:11:11",
                "1133832207",
                "Test Content",
                new Random().nextBoolean()
                );
        log.info("HubLog Msg -> " + hubLogMessage);
        rabbitTemplate.convertAndSend(RabbitMQConstants.SKILL_HUB_LOG_EXCHANGE, RabbitMQConstants.SKILL_HUB_LOG_ROUTE_KEY, hubLogMessage);
    }*/

    /*
    @Scheduled(fixedDelay = 3000L)
    public void sendKeepAliveMsg() {
        KeepAliveMessage keepAliveMessage = new KeepAliveMessage("b8:27:eb:96:e5:b4");
        log.info("Keep-Alive Msg -> " + keepAliveMessage);
        rabbitTemplate.convertAndSend(RabbitMQConstants.SKILL_EXCHANGE, RabbitMQConstants.SKILL_KEEP_ALIVE_ROUTE_KEY, keepAliveMessage);
    }

    @Scheduled(fixedDelay = 3000L)
    public void sendEstablishMessage() {
        EstablishMessage establishMessage = new EstablishMessage("1", 0, "1");
        log.info("Extablish Msg -> " + establishMessage);
        rabbitTemplate.convertAndSend(RabbitMQConstants.SKILL_ESTABLISH_EXCHANGE, RabbitMQConstants.SKILL_ESTABLISH_ROUTE_KEY, establishMessage);
    }
*/
}
