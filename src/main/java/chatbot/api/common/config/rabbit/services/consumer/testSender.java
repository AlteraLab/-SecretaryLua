package chatbot.api.common.config.rabbit.services.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
*/
 /*   @Scheduled(fixedDelay = 10000L)
    public void sendDevMessage() {
        DevMessage devMessage = new DevMessage("b8:27:eb:96:e5:b4", "22:22:22:22:22:22", "aaaabbbbccccddddeeeeffffgggghhhh", "Hello");
        log.info("Dev Msg -> " + devMessage);
        rabbitTemplate.convertAndSend(RabbitMQConstants.SKILL_DEV_EXCHANGE, RabbitMQConstants.SKILL_DEV_ROUTE_KEY, devMessage);

        //sendToUserService.sendDevMsgToUser(devMessage);
    }*/
}
