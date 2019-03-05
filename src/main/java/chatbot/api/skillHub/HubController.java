package chatbot.api.skillHub;

import chatbot.api.common.domain.ResponseDto;
import chatbot.api.common.security.UserPrincipal;
import chatbot.api.mappers.HubMapper;
import chatbot.api.skillHub.domain.*;
import chatbot.api.skillHub.services.HubDeleter;
import chatbot.api.skillHub.services.HubGetter;
import chatbot.api.skillHub.services.HubRegister;
import chatbot.api.skillHub.services.HubUserRegister;
import chatbot.api.user.domain.UserRegisterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
public class HubController {

    @Autowired
    private HubRegister hubRegister;

    @Autowired
    private HubDeleter hubDeleter;

    @Autowired
    private HubGetter hubGetter;

    @Autowired
    private HubMapper hubMapper;

    @Autowired
    private HubUserRegister hubUserRegister;

    @Autowired
    private RestTemplate deleteIpInHub;



    // 특정 유저가 컨트롤할 수 있는 허브 모두 조회하는 메소드
    @GetMapping("/hub")
    public ResponseDto getSpecificUserHub(@AuthenticationPrincipal UserPrincipal userPrincipal){

        Optional<HubInfoDto> hubs = hubMapper.getUserHub(userPrincipal.getId());

        return ResponseDto.builder()
                .msg("success")
                .status(HttpStatus.OK)
                .data(hubs)
                .build();
    }



    // get hubInfo<Long == hubSeq, String == hubName> by adminId
    // 사용자가 허브 조회를 누르면 hubSeq와 hubName을 화면에 뿌려주는 메소드
    @GetMapping("/hubInfo/SeqAndName")
    public ResponseDto getHubsSeqAndNameByAdminId(@AuthenticationPrincipal UserPrincipal userPrincipal) {

        // get hubs by adminId
        List<HubTableVo> hubInfoList = hubGetter.getHubsInfoByadminId(userPrincipal.getId());
        if(hubInfoList == null) {
            return ResponseDto.builder()
                    .msg("fail : 당신이 admin으로 등록된 허브가 없습니다.")
                    .status(HttpStatus.OK)
                    .data(null)
                    .build();
        }

        // set hubsSeqAndName,  <Long == hubSeq, String == hubName>
        Map<Long, String> hubsSeqAndName = new HashMap<Long, String>();
        for(int i = 0; i < hubInfoList.size(); i++) {
            hubsSeqAndName.put(hubInfoList.get(i).getHub_sequence(), hubInfoList.get(i).getHub_name());
        }

        // return <Long == hubSeqs, String == hubNames>
        // react app 에서는 hubsSeqAndName을 파싱해서 화면에 뿌려주는 코드 작성 필요
        return ResponseDto.builder()
                .msg("get success : getHubsInfo")
                .status(HttpStatus.OK)
                .data(hubsSeqAndName)
                .build();
    }



    // hub를 삭제해주는 메소드, {hubSeq} : 삭제할 허브 시퀀스
    @DeleteMapping("/hub/{hubSeq}")
    public ResponseDto deleteHub(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                 @PathVariable(value = "hubSeq") Long hubSeq) {

        Long adminSeq = userPrincipal.getId();

        HubUserInfoDto role = new HubUserInfoDto().builder()
                .hubSeq(hubSeq)
                .userSeq(adminSeq)
                .build();

        ResponseDto responseDto = new ResponseDto().builder()
                .status(HttpStatus.ACCEPTED)
                .build();

        // 관리자 체크
        HubInfoDto hub = hubMapper.getHubInfo(hubSeq);

        if(hub == null) {
            responseDto.setMsg("delete fail : 존재하지 않는 허브 입니다.");
            return responseDto;
        }

        if(!adminSeq.equals(hub.getAdminSeq())) {
            responseDto.setMsg("delete fail : 허브는 존재하지만, 당신은 해당 허브의 관리자가 아닙니다.");
            return responseDto;
        }

        // 허브 서버로 db에 저장된 ip 정보 말소 요청
        String url = "http://" + hub.getExternalIp() + "/" + hub.getExternalPort() + "/ip";
        ResponseEntity<String> resultAboutDelIp = deleteIpInHub.exchange(url, HttpMethod.DELETE, null, String.class);

        if(resultAboutDelIp.equals("fail")) {
            responseDto.setMsg("delete fail : 허브 서버에서 ip를 말소 시키는데 실패했습니다. 나중에 다시 시도해주십시요");
            responseDto.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return responseDto;
        }

        return hubDeleter.explicitDeleter(role);
    }



    // 관리자가 새로운 그룹 유저를 추가해주기 위해 호출하는 메소드
    @PostMapping("/hubUser")
    public ResponseDto addRoll(@AuthenticationPrincipal UserPrincipal userPrincipal,
                               @RequestBody UserRegisterVo userRegisterVo) {

        return hubUserRegister.register(userRegisterVo, userPrincipal.getId());  // (hubId / email / adminId)

        /* 이코드를 위의 return 문으로 수정, 혹시 위의 코드가 에러가 발생할까봐 남겨뒀음
        try {
            responseDto = skillHubUserRegister.register(userRegisterVo, userPrincipal.getId());  // (hubId / email / adminId)
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return responseDto;
        }*/
    }



    // 허브 최초 등록, 순서 : hub 등록 -> hub_user 등록
    @PostMapping("/hub")
    public ResponseDto addHub(@AuthenticationPrincipal UserPrincipal userPrincipal,
                              @RequestBody HubInfoVo hubInfoVo) {

        HubInfoDto hub = HubInfoDto.builder()
                .hubName(hubInfoVo.getHubName())
                .externalIp(hubInfoVo.getExternalIp())
                .externalPort(hubInfoVo.getExternalPort())
                .internalIp(hubInfoVo.getInternalIp())
                .internalPort(hubInfoVo.getInternalPort())
                .beforeIp(null)
                .adminSeq(userPrincipal.getId())
                .lastUsedTime(new Date())
                .build();

        // not yet set hubSeq
        HubUserInfoDto role = HubUserInfoDto.builder()
                .userSeq(userPrincipal.getId())    // userPrincipal.getId();
                .role("ROLE_ADMIN")
                .build();

//        정상적으로 저장시 허브 서버에 "success"를 전송한다.
//        정보를 받은 허브 서버는 react app으로 success status 전달한 이후에 허브는 사설 ip 저장 -> station 모드 실행
        return hubRegister.register(hub, role);
    }
}