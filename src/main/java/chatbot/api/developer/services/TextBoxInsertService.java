package chatbot.api.developer.services;

import chatbot.api.developer.domain.*;
import chatbot.api.mappers.DeveloperMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
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

        List<DvlpEventDTO> dvlpEventList = dvlpDeploymentDTO.getEvents();
        List<DvlpNotifyBoxDTO> dvlpNotifyBoxList = dvlpDeploymentDTO.selectDvlpNotifyBoxList(dvlpEventList);
        List<DvlpThirdServerDTO> dvlpThirdServerList = dvlpDeploymentDTO.selectDvlpThirdServerList(dvlpEventList);
        List<DvlpControlDTO> dvlpControlList = dvlpDeploymentDTO.selectDvlpControlList(dvlpEventList);

        List<DvlpDerivationDTO> dvlpDerivationList = dvlpDeploymentDTO.getDerivationDTOList();

        if(device != null) {
            developerMapper.insertDevice(device);
        }
        if(dvlpBoxList != null) {
            developerMapper.insertTextBoxes(dvlpBoxList, devId);
        }
        if(dvlpDataModelList != null) {
            developerMapper.insertDataModels(dvlpDataModelList, devId);
        }
        if(dvlpButtonList != null) {
            developerMapper.insertButtons(dvlpButtonList, devId);
        }
        if(dvlpStateRuleList != null) {
            developerMapper.insertRules(dvlpStateRuleList, devId);
        }
        if(dvlpEventList != null) {
            developerMapper.insertEvents(dvlpEventList, devId);
        }
        if(dvlpNotifyBoxList != null) {
            developerMapper.insertNotifyBoxs(dvlpNotifyBoxList);
        }
        if(dvlpThirdServerList != null) {
            developerMapper.insertThirdServers(dvlpThirdServerList);
        }
        if(dvlpControlList != null) {
            developerMapper.insertControls(dvlpControlList);
        }
        if(dvlpDerivationList != null) {
            developerMapper.insertDerivations(dvlpDerivationList, devId);
        }
    }
}
