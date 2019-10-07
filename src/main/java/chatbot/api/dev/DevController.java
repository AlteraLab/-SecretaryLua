package chatbot.api.dev;

import chatbot.api.dev.services.DevSelectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class DevController {

    @Autowired
    private DevSelectService devSelectService;


    @GetMapping("/model/{devType}")
    public Object getDevDataModel(@PathVariable("devType") String devType) {
        return devSelectService.getDataModelByDevType(devType);
    }
}