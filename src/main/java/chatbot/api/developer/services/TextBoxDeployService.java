package chatbot.api.developer.services;

import chatbot.api.developer.domain.DvlpDeploymentDTO;
import chatbot.api.developer.domain.DvlpDevDTO;
import chatbot.api.mappers.DeveloperMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TextBoxDeployService {

    private DeveloperMapper developerMapper;

    private TextBoxDeleteService textBoxDeleteService;

    private TextBoxInsertService textBoxInsertService;


    @Transactional(rollbackFor = Exception.class)
    public void deploy(DvlpDeploymentDTO dvlpDeploymentDTO) {
        DvlpDevDTO device = developerMapper.getDevice(dvlpDeploymentDTO.getDevice().getDevId());
        if(device == null) {
            textBoxInsertService.multipleTableMultipleInsert(dvlpDeploymentDTO);
        } else {
            textBoxDeleteService.multipleTableMultipleDelete(dvlpDeploymentDTO);
            textBoxInsertService.multipleTableMultipleInsert(dvlpDeploymentDTO);
        }
    }
}