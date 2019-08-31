package chatbot.api.common.config.rabbit.services.consumer;

import chatbot.api.common.config.rabbit.RabbitMQConstants;
import chatbot.api.common.config.rabbit.domain.sub.EstablishMessage;
import chatbot.api.mappers.HubMapper;
import chatbot.api.skillhub.repository.KeepAliveRepository;
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
    private KeepAliveRepository keepAliveRepository;


    @RabbitListener(queues = {RabbitMQConstants.SKILL_ESTABLISH_QUEUE})
    public void consumeEstablishMessage(final EstablishMessage establishMessage) {
        log.info("=========== Consume Establish Message 시작 ===========");
        log.info("Establish Queue 에 데이터가 들어왔습니다.");
        log.info("EstablishMessage : " + establishMessage.toString());
        // 1. DB 에 ip / port / mac 을 주고 state 수정
        hubMapper.editStateToTrueWhenEstablish(
                establishMessage.getMac(),
                establishMessage.getIp(),
                establishMessage.getPort()
        );
        keepAliveRepository.save(establishMessage.getMac(), true);
        log.info("=========== Consume Establish Message 종료 ===========");
    }
}