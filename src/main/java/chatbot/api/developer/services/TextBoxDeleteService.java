package chatbot.api.developer.services;

import chatbot.api.developer.domain.DvlpDeploymentDTO;
import chatbot.api.mappers.DeveloperMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TextBoxDeleteService {

    @Autowired
    private DeveloperMapper developerMapper;


    @Transactional(rollbackFor = Exception.class)
    public void multipleTableMultipleDelete(DvlpDeploymentDTO dvlpDeploymentDTO) {
        Long devId = dvlpDeploymentDTO.getDevice().getDevId();
        developerMapper.deleteDerivations(devId);
        developerMapper.deleteEvents(devId);
        developerMapper.deleteRules(devId);
        developerMapper.deleteButtons(devId);
        developerMapper.deleteTextBoxes(devId);
        developerMapper.deleteDataModels(devId);
        developerMapper.deleteDevice(devId);
    }
}
