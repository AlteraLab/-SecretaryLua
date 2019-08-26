package chatbot.api.dev.services;

import chatbot.api.common.domain.ResponseDTO;
import chatbot.api.dev.domain.EventVO;
import chatbot.api.mappers.DataModelMapper;
import chatbot.api.mappers.HrdwrMapper;
import chatbot.api.textbox.domain.path.HrdwrDTO;
import chatbot.api.textbox.domain.textboxdata.DataModelDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
        log.info("data Model -> " + dev.getHrdwrId() + ", " + dataModelMapper.getDataModelsByHrdwrId(dev.getHrdwrId()));
        log.info("events -> " + dev.getHrdwrId() + ", " + dataModelMapper.getAllEvents(dev.getHrdwrId()));

        return ResponseDTO.builder()
                .status(HttpStatus.OK)
                .msg("Success")
                .data(new Object(){
                    public List<DataModelDTO> model = dataModelMapper.getDataModelsByHrdwrId(dev.getHrdwrId());
                    public List<EventVO> events = dataModelMapper.getAllEvents(dev.getHrdwrId());
                })
                .build();
    }
}
