package chatbot.api.hublog;

import chatbot.api.common.domain.ResponseDTO;
import chatbot.api.common.security.UserPrincipal;
import chatbot.api.hublog.domain.HubLogDTO;
import chatbot.api.hublog.services.HubLogSelectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class HubLogController {

    @Autowired
    private HubLogSelectService hubLogSelectService;


    @GetMapping("/hubs/{hubMac}/logs")
    public ResponseDTO getLogs(@AuthenticationPrincipal UserPrincipal userPrincipal,
                               @PathVariable("hubMac") String hubMac) {
        System.out.println("\n");
        log.info("============= get Logs =============");
        log.info("Info -> hubMac : " + hubMac);

        return hubLogSelectService.getHubLogByHubMac(hubMac);
    }
}
