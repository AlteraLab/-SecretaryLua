package chatbot.api.developer;

import chatbot.api.common.domain.ResponseDTO;
import chatbot.api.developer.domain.DvlpDeploymentDTO;
import chatbot.api.developer.domain.DvlpDevDTO;
import chatbot.api.developer.services.TextBoxDeployService;
import chatbot.api.mappers.DeveloperMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static chatbot.api.developer.utils.developerConstants.FAIL_MSG_DEPLOY;
import static chatbot.api.developer.utils.developerConstants.SUCCESS_MSG_DEPLOY;

@RestController
@Slf4j
public class DeveloperController {

    @Autowired
    private TextBoxDeployService textBoxDeployService;


    @PostMapping("/deploy")
    public ResponseDTO deploy(@RequestBody DvlpDeploymentDTO dvlpDeploymentDTO) {

        log.info("==================== deploy 시작 ====================");
        log.info(dvlpDeploymentDTO.getDevice().toString());
        log.info(dvlpDeploymentDTO.getBoxDTOList().toString());
        log.info(dvlpDeploymentDTO.getDataModelDTOList().toString());
        log.info(dvlpDeploymentDTO.getButtonDTOList().toString());
        log.info(dvlpDeploymentDTO.getStateRuleDTOList().toString());
        //log.info(dvlpDeploymentDTO.getEventDTOList().toString()); 이벤트 관련
        log.info(dvlpDeploymentDTO.getDerivationDTOList().toString());

        ResponseDTO responseDTO = ResponseDTO.builder()
                .status(HttpStatus.OK)
                .msg(SUCCESS_MSG_DEPLOY)
                .build();
        try {
            this.textBoxDeployService.deploy(dvlpDeploymentDTO);
        } catch (Exception e) {
            responseDTO.setMsg(FAIL_MSG_DEPLOY);
            responseDTO.setStatus(HttpStatus.EXPECTATION_FAILED);
            e.printStackTrace();
        }
        return responseDTO;
    }
}
