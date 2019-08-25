package chatbot.api.dev.services;

import chatbot.api.common.domain.ResponseDTO;
import chatbot.api.mappers.DataModelMapper;
import chatbot.api.mappers.HrdwrMapper;
import chatbot.api.textbox.domain.path.HrdwrDTO;
import chatbot.api.textbox.domain.textboxdata.DataModelDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DevSelectService {

    @Autowired
    private DataModelMapper dataModelMapper;

    @Autowired
    private HrdwrMapper hrdwrMapper;


    public Object getDataModelByDevType(String devType) {
        HrdwrDTO dev = hrdwrMapper.getHrdwrByAuthKey(devType);
        //ArrayList<DataModelDTO> dataModels = dataModelMapper.getDataModelsForTableByHrdwrId(dev.getHrdwrId());
        //ArrayList<DataModelDTO> dataModels = dataModelMapper.getDataModelsByHrdwrId(dev.getHrdwrId());
        log.info("data Model -> " + dev.getHrdwrId() + ", " + dataModelMapper.getDataModelsByHrdwrId(dev.getHrdwrId()));
        return ResponseDTO.builder()
                .status(HttpStatus.OK)
                .msg("Success")
                .data(new Object(){
                    public List<DataModelDTO> model = dataModelMapper.getDataModelsByHrdwrId(dev.getHrdwrId());
                })
                .build();
    }
}
