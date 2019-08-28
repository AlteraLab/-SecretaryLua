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
        if(dvlpBoxList.size() != 0) {
            developerMapper.insertTextBoxes(dvlpBoxList, devId);
        }
        if(dvlpDataModelList.size() != 0) {
            developerMapper.insertDataModels(dvlpDataModelList, devId);
        }
        if(dvlpButtonList.size() != 0) {
            developerMapper.insertButtons(dvlpButtonList, devId);
        }
        if(dvlpEventList.size() != 0) {
            developerMapper.insertEvents(dvlpEventList, devId);
        }
        if(dvlpStateRuleList.size() != 0) {
            developerMapper.insertRules(dvlpStateRuleList, devId);
        }
        if(dvlpNotifyBoxList.size() != 0) {
            developerMapper.insertNotifyBoxs(dvlpNotifyBoxList);
        }
        if(dvlpThirdServerList.size() != 0) {
            developerMapper.insertThirdServers(dvlpThirdServerList);
        }
        if(dvlpControlList.size() != 0) {
            developerMapper.insertControls(dvlpControlList);
        }
        if(dvlpDerivationList.size() != 0) {
            developerMapper.insertDerivations(dvlpDerivationList, devId);
        }
    }
}
