package chatbot.api.common.services;

import chatbot.api.textbox.domain.Build;
import chatbot.api.textbox.domain.path.HrdwrDTO;
import chatbot.api.textbox.domain.response.ResponseHrdwrInfo;
import chatbot.api.textbox.repository.BuildRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
@Slf4j
public class RestTemplateService {

    private RestTemplate restTemplate;

    private BuildRepository buildRepository;


    public ResponseHrdwrInfo requestHrdwrsInfo(String providerId) {
        log.info("RestTemplate -> requestHrdwrsInfo 시작");
        Build reBuild = buildRepository.find(providerId);
        String url = "http://" + reBuild.getPath().getExternalIp() + ":" + reBuild.getPath().getExternalPort() + "/dev";
        log.info("INFO >> 전달할 URL 주소 : " + url);
        log.info("RestTemplate -> requestHrdwrsInfo 끝");

        //log.info("INFO >> DEV INFO 확인 : " + hrdwrInfo.toString());
        //return restTemplate.getForObject(url, ResponseHrdwrInfo.class);

        // 일단 데이터 받은걸로 가정합시다.
        HrdwrDTO[] hrdwrs = new HrdwrDTO[2];
        hrdwrs[0] = HrdwrDTO.builder()
                .hrdwrMac("12:12:12:12:12:12")
                .authKey("b776d155a4d44816969408f831600e2d")
                .build();
        hrdwrs[1] = HrdwrDTO.builder()
                .hrdwrMac("72:72:72:72:72:72")
                .authKey("aaaabbbbccccddddeeeeffffgggghhhh")
                .build();

        ResponseHrdwrInfo hrdwrInfo = ResponseHrdwrInfo.builder()
                .hrdwrsInfo(hrdwrs)
                .status(true)
                .build();
        log.info("INFO >> DEV INFO 확인 : " + hrdwrInfo.toString());
        return hrdwrInfo;
    }
}
