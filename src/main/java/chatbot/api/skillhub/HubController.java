package chatbot.api.skillhub;

import chatbot.api.mappers.RoleMapper;
import chatbot.api.role.domain.RoleDTO;
import chatbot.api.common.domain.ResponseDTO;
import chatbot.api.common.security.UserPrincipal;
import chatbot.api.mappers.HubMapper;
import chatbot.api.skillhub.domain.*;
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


    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////


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


    // 허브 등록 순서 : hub 등록 -> hub_user 등록
    //@PostMapping("/hub/{userId}")
    @PostMapping("/hub")
    public ResponseDTO registHub(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                 @Valid @RequestBody HubVO hub) {

        log.info(hub.toString());
        return hubRegisterService.register(userPrincipal.getId(), hub);
        //return hubRegisterService.register(new Long(1), hub);
    }


    // hub를 삭제해주는 메소드, 나중에 허브에 대한 모듈 테이블이 자식 테이블로 생성될 시 자식 테이블의 모듈들도 제거해주는 코드 작성
    // 추후에 허브에 모듈이 붙으면 모듈들에 데이터를 제거하는 코드도 추가해야함.
    @DeleteMapping("/hub")
    public ResponseDTO deleteHub(//@PathVariable("userId") Long userId,
                                 @AuthenticationPrincipal UserPrincipal userPrincipal,
                                 @RequestBody HubVO hubInfoVo) {

        RoleDTO role = new RoleDTO().builder()
                //.userSeq(userId)
                .userId(userPrincipal.getId())
                .hubId(hubInfoVo.getHubId())
                .build();

        ResponseDTO responseDto = new ResponseDTO().builder()
                .status(HttpStatus.ACCEPTED)
                .build();


        HubInfoDTO hub = hubMapper.getHubInfo(hubInfoVo.getHubId());
        if (hub == null) {
            responseDto.setMsg(FAIL_MSG_BECAUSE_NO_EXIST);
            return responseDto;
        }

        // ROLE_USER   VS   ROLE_ADMIN
//        if(!userId.equals(hub.getAdminSeq())) {     // ROLE_USER
        if (!userPrincipal.getId().equals(hub.getAdminId())) {     // ROLE_USER

            // 1. 해당 유저가 ROLE_USER 인지 확인
            //RoleDto roleUser = roleMapper.getRoleInfo(hubInfoVo.getHubSequence(), userId);
            RoleDTO roleUser = roleMapper.getRoleInfo(hubInfoVo.getHubId(), userPrincipal.getId());
            if (roleUser == null) {
                responseDto.setMsg(FAIL_MSG_NO_ROLE_USER_AND_ROLE_ADMIN);
                responseDto.setStatus(HttpStatus.NO_CONTENT);
                return responseDto;
            }

            return hubDeleteService.explicitDeleterByUser(role);
            // 2. 한 번 더 확인 (만약 사용 가능하다면, 허브 사용 명단에서 유저를 제거)
     /*       if(roleUser.getRole().equals(ROLE_USER)) {
                return hubDeleter.explicitDeleterByUser(role);
            } else {
                responseDto.setMsg(FAIL_MSG);
                return responseDto;
            }*/

        } else {     // ROLE_ADMIN
            // 허브 서버로 db에 저장된 ip 정보 말소 요청
            String url = "http://" + hub.getExternalIp() + "/" + hub.getExternalPort() + "/ip";
            ResponseEntity<String> resultAboutDelIp = deleteIpInHub.exchange(url, HttpMethod.DELETE, null, String.class);

            if (resultAboutDelIp.equals("fail")) {
                responseDto.setMsg(FAIL_MSG_DELETE_HUB_BECAUSE_FAIL_RESTTEMPLATE);
                responseDto.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
                return responseDto;
            }

            // 나중에 허브에 대한 모듈 테이블이 자식 테이블로 생성될 시 자식 테이블을 모듈들도 제거해주는 코드 작성
            return hubDeleteService.explicitDeleterByAdmin(role);
        }
    }
}
/*
    // hub edit, admin만 수행 가능
    @PutMapping("/hub")
    public ResponseDto editHub(//@PathVariable("userId") Long userId,
                               @AuthenticationPrincipal UserPrincipal userPrincipal,
                               @RequestBody HubVo hubInfoVo) {

        ResponseDto responseDto = new ResponseDto().builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();

        log.info(hubInfoVo.toString());

        responseDto.setMsg(FAIL_MSG_NO_EXIST_HUB);
        HubInfoDto hub = hubMapper.getHubInfo(hubInfoVo.getHubId());
        if(hub == null)                 return responseDto;

        log.info(hub.toString());

        responseDto.setMsg(FAIL_MSG_TO_EDIT_HUB_BECAUSE_NO_ADMIN);
        if(userPrincipal.getId() != hub.getAdminId()) return responseDto;
        //if(userId != hub.getAdminSeq()) return responseDto;

        return ResponseDto.builder().build();

        return hubEditService.editer(hubInfoVo.getHubId(),
                                     hubInfoVo.getExternalIp(),
                                     hubInfoVo.getInternalIp(),
                                     hubInfoVo.getExternalPort(),
                                     hubInfoVo.getInternalPort());


*/

/*
    // 일단 만들었는데 안쓸수도 있음
    // get hubInfo<Long == hubSeq, String == hubName> by adminId, 수정 필요할듯...
    // 사용자가 허브 조회를 누르면 hubSeq와 hubName을 화면에 뿌려주는 메소드
    @GetMapping("/hubInfo/SeqAndName")
    public ResponseDto getHubsSeqAndNameByAdminId(@AuthenticationPrincipal UserPrincipal userPrincipal) {

        // get hubs by adminId
        List<HubInfoDto> hubInfoList = hubSelectService.getHubsInfoByadminId(userPrincipal.getId());
        if(hubInfoList == null) {
            return ResponseDto.builder()
                    .msg(FAIL_MSG_NO_HUB_REGISTED_AS_ADMIN)
                    .status(HttpStatus.OK)
                    .data(null)
                    .build();
        }

        // set hubsSeqAndName,  <Long == hubSeq, String == hubName>
        Map<Long, String> hubsSeqAndName = new HashMap<Long, String>();
        for(int i = 0; i < hubInfoList.size(); i++) {
            //hubsSeqAndName.put(hubInfoList.get(i).getHub_sequence(), hubInfoList.get(i).getHub_name());
            hubsSeqAndName.put(hubInfoList.get(i).getHubId(), hubInfoList.get(i).getHubName());
        }

        // return <Long == hubSeqs, String == hubNames>
        // react app 에서는 hubsSeqAndName을 파싱해서 화면에 뿌려주는 코드 작성 필요
        return ResponseDto.builder()
                .msg(SUCCESS_MSG_GET_HUBS_SEQ_AND_NAME)
                .status(HttpStatus.OK)
                .data(hubsSeqAndName)
                .build();
    }*/
