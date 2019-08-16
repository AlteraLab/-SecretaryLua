package chatbot.api.skillhub;

import chatbot.api.mappers.RoleMapper;
import chatbot.api.role.domain.RoleDTO;
import chatbot.api.common.domain.ResponseDTO;
import chatbot.api.common.security.UserPrincipal;
import chatbot.api.mappers.HubMapper;
import chatbot.api.skillhub.domain.*;
import chatbot.api.skillhub.repository.KeepAliveRepository;
import chatbot.api.skillhub.services.HubEditService;
import chatbot.api.skillhub.services.HubDeleteService;
import chatbot.api.skillhub.services.HubSelectService;
import chatbot.api.skillhub.services.HubRegisterService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.print.DocFlavor;
import javax.validation.Valid;
import java.util.*;

import static chatbot.api.role.utils.RoleConstants.*;
import static chatbot.api.skillhub.utils.HubConstants.*;

@SuppressWarnings("ALL")
@RestController
@NoArgsConstructor
@Slf4j
public class HubController {

    @Autowired
    private HubRegisterService hubRegisterService;

    @Autowired
    private HubDeleteService hubDeleteService;

    @Autowired
    private HubSelectService hubSelectService;

    @Autowired
    private HubEditService hubEditService;

    @Autowired
    private RestTemplate deleteIpInHub;

    @Autowired
    private HubMapper hubMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private KeepAliveRepository keepAliveRepository;


    // UPnP 수행 이후, 할당 받은 Ip가 이전 Ip와 다르다면 스킬 서버로 데이터를 전송
    // Ip 수정 실시
    /*
    PUT http://localhost:8083/hub/upnpIp HTTP/1.1
    Content-Type: application/json
    {
        "mac_addr": "12:34:56:78:90:12",
        "external_ip": "203.250.32.29",
        "external_port": 540000
    }
    */
    @PutMapping("/hub/upnpIp")
    public ResponseDTO updateUpnpIp(@RequestBody HubVO hubInfoVo) {
        System.out.println("\n");
        log.info("Info -> Update Upnp Ip");
        log.info("MAC -> " + hubInfoVo.getMacAddr() + ", IP -> " + hubInfoVo.getExternalIp() + ", Port -> " + hubInfoVo.getExternalPort());
        return hubEditService.editerHubUpnpIp(hubInfoVo);
    }


    // 허브 등록 순서 : hub 등록 -> hub_user 등록
    @PostMapping("/hub")
    public ResponseDTO registHub(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                 @Valid @RequestBody HubVO hub) {
        log.info("User -> " + userPrincipal);
        log.info("Regist Hub -> " + hub.toString());
        return hubRegisterService.register(userPrincipal.getId(), hub);
    }


    // 특정 유저가 컨트롤 할 수 있는 허브 모두 조회하는 메소드
    // 이거 왜있는지 모르겠음... 나중에 교준이한테 물어보기..
    @GetMapping("/hub")
    public ResponseDTO getSpecificUserHub(@AuthenticationPrincipal UserPrincipal userPrincipal) {

        Optional<HubInfoDTO> hubs = hubMapper.getUserHub(userPrincipal.getId());

        return ResponseDTO.builder()
                .msg("success")
                .status(HttpStatus.OK)
                .data(hubs)
                .build();
    }


    // 허브 삭제
    @DeleteMapping("/hub/{hubId}")
    public ResponseDTO deleteHub(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                 @PathVariable("hubId") Long hubId) {

        return hubDeleteService.deleter(userPrincipal.getId(), hubId);
    }
}