package chatbot.api.developer.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DvlpDeploymentDTO {

    private DvlpDevDTO device;

    private List<DvlpBoxDTO> boxDTOList;

    private List<DvlpDataModelDTO> dataModelList;

    private List<DvlpButtonDTO> buttonDTOList;

    private List<DvlpStateRuleDTO> rules;

    private List<DvlpEventDTO> events;   // 나중에 풀기

    private List<DvlpDerivationDTO> derivationDTOList;


    public List<DvlpNotifyBoxDTO> selectDvlpNotifyBoxList(List<DvlpEventDTO> dvlpEvents) {

        List<DvlpNotifyBoxDTO> dvlpNotifyBoxs = new ArrayList<DvlpNotifyBoxDTO>();

        for(DvlpEventDTO tempEvent : dvlpEvents) {
            if(tempEvent.getOutputType().equals("1")) {
                dvlpNotifyBoxs.add(tempEvent.getNotifyBoxDTO());
            }
        }

        if(dvlpNotifyBoxs.size() == 0) {
            return null;
        }
        return dvlpNotifyBoxs;
    }

    public List<DvlpThirdServerDTO> selectDvlpThirdServerList(List<DvlpEventDTO> dvlpEvents) {

        List<DvlpThirdServerDTO> dvlpThirdServers = new ArrayList<DvlpThirdServerDTO>();

        for(DvlpEventDTO tempEvent : dvlpEvents) {
            if(tempEvent.getOutputType().equals("3")) {
                dvlpThirdServers.add(tempEvent.getThirdServerDTO());
            }
        }

        if(dvlpThirdServers.size() == 0) {
            return null;
        }
        return dvlpThirdServers;
    }

    public List<DvlpControlDTO> selectDvlpControlList(List<DvlpEventDTO> dvlpEvents) {

        List<DvlpControlDTO> dvlpControls = new ArrayList<DvlpControlDTO>();

        for(DvlpEventDTO tempEvent : dvlpEvents) {
            if(tempEvent.getOutputType().equals("2")) {
                dvlpControls.add(tempEvent.getControlDTO());
            }
        }

        if(dvlpControls.size() == 0) {
            return null;
        }
        return dvlpControls;
    }

}
