package chatbot.api.textbox.services;

import chatbot.api.textbox.domain.response.ResponseHrdwrInfo;
import chatbot.api.textbox.repository.BuildRepository;
import chatbot.api.common.services.KakaoSimpleTextService;
import chatbot.api.common.services.RestTemplateService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class BuildActionService {

    private BuildRepository buildRepository;

    private BuildSaveService buildSaveService;

    private RestTemplateService restTemplateService;


    public Boolean actionRequestAndSaverAboutHrdwr(String providerId) {

        log.info("허브와 연결된 장치가 있는지 체크");
        ResponseHrdwrInfo hrdwrInfo = restTemplateService.requestHrdwrsInfo(providerId);
        if(hrdwrInfo.hasNull()) {
            buildRepository.delete(providerId);
            log.info("허브와 연결된 장치가 없다");
            return true;
        }
        log.info("허브와 연결된 장치가 있다");
        buildSaveService.saverHrdwrs(providerId, hrdwrInfo.getDevInfo());
        return false;
    }
}
