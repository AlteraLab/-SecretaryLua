package chatbot.api.hublog;

import chatbot.api.common.domain.ResponseDTO;
import chatbot.api.common.security.UserPrincipal;
import chatbot.api.hublog.domain.HubLogDTO;
import chatbot.api.hublog.services.HubLogRecordService;
import chatbot.api.hublog.services.HubLogSelectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class HubLogController {

    @Autowired
    private HubLogRecordService hubLogRecordService;

    @Autowired
    private HubLogSelectService hubLogSelectService;



    @PutMapping("/hub/log")
    public ResponseDTO recordLog(@RequestBody HubLogDTO hubLogDto) {
        System.out.println("\n");
        log.info("============= Record Log =============");
        log.info("Info -> HubLogDto : " + hubLogDto.toString());

        return hubLogRecordService.record(hubLogDto);
    }


    @GetMapping("/hubs/{hubId}/logs")
    public ResponseDTO getLogs(@AuthenticationPrincipal UserPrincipal userPrincipal,
                               @PathVariable("hubId") Long hubId) {
        System.out.println("\n");
        log.info("============= get Logs =============");
        log.info("Info -> hubId : " + hubId);

        return hubLogSelectService.getHubLogByHubId(hubId);
    }
}
