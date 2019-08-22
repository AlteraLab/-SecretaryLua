package chatbot.api.developer.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DvlpDeploymentDTO {

    private DvlpDevDTO device;

    private List<DvlpBoxDTO> boxDTOList;

    private List<DvlpDataModelDTO> dataModelDTOList;

    private List<DvlpButtonDTO> buttonDTOList;

    private List<DvlpStateRuleDTO> stateRuleDTOList;

    //private List<DvlpEventDTO> eventDTOList;   // 나중에 풀기

    private List<DvlpDerivationDTO> derivationDTOList;

}
