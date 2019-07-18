package chatbot.api.common.config.rabbit.services.publisher;

import chatbot.api.common.config.rabbit.domain.pub.EstablishResultMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class EstablishResultMessagePublisher {

    @Autowired
    private RestTemplate restTemplate;


    public void publishEstablishResultToAnyHubMQ(EstablishResultMessage establishResultMessage, String hubIp, Integer hubPort) {
        log.info("======== Publish Establishment Result Message To Any Hub Message Queue 시작 ========");

        String url = "http://" + hubIp + ":" + hubPort + "/establishment/result";
        log.info("URL -> " + url);
        log.info( url+ " 로 데이터를 전달하였습니다. ( RestTemplate )");
        /*restTemplate.postForObject(
                url,
                new Object(){
                    public Boolean status = establishResultMessage.getStatus();
                },
                null);*/
        log.info("======== Send Establishment Result Message To Any Hub Message Queue 종료 ========");
    }
}
