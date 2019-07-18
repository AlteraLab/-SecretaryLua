package chatbot.api.common.config.rabbit.services.consumer.establish;

import chatbot.api.common.config.rabbit.RabbitMQConstants;
import chatbot.api.common.config.rabbit.domain.pub.EstablishResultMessage;
import chatbot.api.common.config.rabbit.domain.sub.EstablishMessage;
import chatbot.api.common.config.rabbit.services.publisher.EstablishResultMessagePublisher;
import chatbot.api.mappers.HubMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EstablishMessageConsumer {

    @Autowired
    private HubMapper hubMapper;

    @Autowired
    private EstablishResultMessagePublisher establishResultMessagePublisher;


    @RabbitListener(queues = {RabbitMQConstants.SKILL_ESTABLISH_QUEUE})
    public void consumeEstablishMessage(final EstablishMessage establishMessage) {
        log.info("=========== Consume Establish Message 시작 ===========");

        log.info("Establish Queue 에 데이터가 들어왔습니다.");
        log.info("Data : " + establishMessage.toString());

        log.info("Ip -> " + establishMessage.getIp());
        log.info("Port -> " + establishMessage.getPort());
        log.info("Mac -> " + establishMessage.getMac());

        Boolean hasException = false;

        try {
            // 1. DB 에 ip / port / mac 을 주고 state 수정
            hubMapper.editStateToTrueWhenEstablish(
                    establishMessage.getMac(),
                    establishMessage.getIp(),
                    establishMessage.getPort()
            );
        } catch (Exception e) {
            hasException = true;
            e.printStackTrace();
            log.info("=========== Consume Establish Message 예외 발생 ===========");
        }

        // 2.
        // producer를 이용해서 hub에게 수립 결과 전달
        // true 값 전달 시, keepalive 실행
        // hasException == true  -> message.status false
        EstablishResultMessage establishResultMessage = new EstablishResultMessage();
        if(hasException) {
            establishResultMessage.setStatus(false);
            log.info("Establish Result Message -> FALSE");
        } else {
            log.info("Establish Result Message -> TRUE");
        }

        establishResultMessagePublisher.publishEstablishResultToAnyHubMQ(
                establishResultMessage,
                establishMessage.getIp(),
                establishMessage.getPort()
        );

        log.info("=========== Consume Establish Message 종료 ===========");
    }
}
