package chatbot.api.hub;

import chatbot.api.common.domain.ResponseDto;
import chatbot.api.common.security.UserPrincipal;
import chatbot.api.hub.domain.HubInfoDto;
import chatbot.api.hub.domain.UserHubDto;
import chatbot.api.hub.services.HubRegister;
import chatbot.api.mappers.HubMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class HubController {

    @Autowired
    private HubMapper hubMapper;

    @Autowired
    private HubRegister hubRegister;

    @GetMapping("/hub")
    public ResponseDto getSpecificUserHub(@AuthenticationPrincipal UserPrincipal userPrincipal){

        Optional<HubInfoDto> hubs = hubMapper.getUserHub(userPrincipal.getId());

        return ResponseDto.builder()
                .msg("success")
                .status(HttpStatus.OK)
                .data(hubs)
                .build();
    }

    @PostMapping("/hub")
    public ResponseDto test(
            @RequestParam(value="exIp",required = true) String externalIp,
            @RequestParam(value="exPort",required = true) int externalPort,
            @RequestParam(value="inIp",required = true) String internalIp,
            @RequestParam(value="inPort",required = true) int internalPort,
            @AuthenticationPrincipal UserPrincipal userPrincipal){

        HubInfoDto hub = HubInfoDto.builder()
                .externalIp(externalIp)
                .externalPort(externalPort)
                .internalIp(internalIp)
                .internalPort(internalPort)
                .build();

        UserHubDto role = UserHubDto.builder()
                .userId(userPrincipal.getId())
                .role("ROLE_ADMIN")
                .build();

        try {
            hubRegister.register(hub,role);
        } catch (Exception e) {
            e.printStackTrace();

            return ResponseDto.builder()
                    .msg("fail")
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }

        return ResponseDto.builder()
                .msg("success")
                .status(HttpStatus.OK)
                .build();
    }
}
