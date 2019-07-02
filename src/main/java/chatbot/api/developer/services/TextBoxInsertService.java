package chatbot.api.developer.services;

import chatbot.api.developer.domain.*;
import chatbot.api.mappers.DeveloperMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TextBoxInsertService {

    @Autowired
    private DeveloperMapper developerMapper;

    @Transactional(rollbackFor = Exception.class)
    public void multipleTableMultipleInsert(DvlpDeploymentDTO dvlpDeploymentDTO) {
        Long devId = dvlpDeploymentDTO.getDevice().getDevId();
        DvlpDevDTO device = dvlpDeploymentDTO.getDevice();
        List<DvlpBoxDTO> dvlpBoxList = dvlpDeploymentDTO.getBoxDTOList();
        List<DvlpButtonDTO> dvlpButtonList = dvlpDeploymentDTO.getButtonDTOList();
        List<DvlpDerivationDTO> dvlpDerivationList = dvlpDeploymentDTO.getDerivationDTOList();

        developerMapper.insertDevice(device);
        developerMapper.insertTextBoxes(dvlpBoxList, devId);
        developerMapper.insertButtons(dvlpButtonList, devId);
        developerMapper.insertDerivations(dvlpDerivationList, devId);
    }
}
