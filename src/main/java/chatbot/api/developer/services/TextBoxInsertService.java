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
        List<DvlpDataModelDTO> dvlpDataModelList = dvlpDeploymentDTO.getDataModelList();
        List<DvlpButtonDTO> dvlpButtonList = dvlpDeploymentDTO.getButtonDTOList();
        List<DvlpStateRuleDTO> dvlpStateRuleList = dvlpDeploymentDTO.getRules();
        // 이벤트 관련 코드 부분 ...
        List<DvlpDerivationDTO> dvlpDerivationList = dvlpDeploymentDTO.getDerivationDTOList();

        developerMapper.insertDevice(device);
        developerMapper.insertTextBoxes(dvlpBoxList, devId);
        developerMapper.insertDataModels(dvlpDataModelList, devId);
        developerMapper.insertButtons(dvlpButtonList, devId);
        developerMapper.insertRules(dvlpStateRuleList, devId);
        // 이벤트 관련 코드 부분 ...
        developerMapper.insertDerivations(dvlpDerivationList, devId);
    }
}
