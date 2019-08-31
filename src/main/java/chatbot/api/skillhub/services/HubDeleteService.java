package chatbot.api.skillhub.services;

import chatbot.api.common.domain.ResponseDTO;
import chatbot.api.common.services.RestTemplateService;
import chatbot.api.mappers.HubMapper;
import chatbot.api.mappers.RoleMapper;
import chatbot.api.role.domain.RoleDTO;
import chatbot.api.skillhub.domain.HubInfoDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import static chatbot.api.role.utils.RoleConstants.SUCCESS_MSG_DELETE_ROLE_USER;
import static chatbot.api.skillhub.utils.HubConstants.*;

@Service
@Slf4j
@AllArgsConstructor
public class HubDeleteService {

    private HubMapper hubMapper;

    private RoleMapper roleMapper;

    private RestTemplate restTemplate;


    // admin이 해당 허브에 대한 정보 모두 삭제
    // https://enterkey.tistory.com/275
    // https://cakas.tistory.com/9
    // https://a1010100z.tistory.com/entry/Spring-ResponseEity%EB%8A%94-%EC%99%9C-%EC%93%B0%EB%8A%94-%EA%B2%83%EC%9D%B4%EB%A9%B0-%EC%96%B4%EB%96%BB%EA%B2%8C-%EC%93%B0%EB%8A%94%EA%B1%B8%EA%B9%8C
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO deleter(Long adminId, Long hubId) {
        log.info("Deleter ");
        ResponseDTO responseDTO = new ResponseDTO(null, HttpStatus.OK, null);
        Boolean exceptionFlag = false;
        HubInfoDTO hub = hubMapper.getHubInfo(hubId);

        if(hub == null) {
            responseDTO.setMsg("이미 존재하지 않는 허브 입니다.");
        }
        if(!hub.getAdminId().equals(adminId)) {
            responseDTO.setMsg("권한이 없습니다.");
        }

        try {
            roleMapper.deleteRoleWithHubId(hub.getHubId());
            hubMapper.deleteHub(hub.getHubId());

            String url = "http://" + hub.getExternalIp() + ":" + hub.getExternalPort() + "/hub";
           /* ResponseEntity<String> responseEntity =
                    restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);*/
            ResponseEntity<String> responseEntity = new ResponseEntity<String>(HttpStatus.OK); // 임시 코드

            if(responseEntity.getStatusCode() != HttpStatus.OK) {
                log.info("responseEntity.getStatusCode() != HttpStatus.OK");
                throw new Exception();
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info(EXCEPTION_MSG_DURING_DELETER);
            responseDTO.setMsg(EXCEPTION_MSG_DURING_DELETER);
            exceptionFlag = true;
        }

        if(hub != null && exceptionFlag != true) {
            responseDTO.setMsg("허브를 삭제했습니다.");
            log.info("허브를 삭제했습니다.");
            responseDTO.setData(hubId);
        }

        return responseDTO;
    }
}
/*
    // 허브에 대한 일반 유저가 스스로 허브에 대한 권한을 스스로 ㄹ제거
    public ResponseDTO explicitDeleterByUser(RoleDTO role) {

        ResponseDTO responseDto = ResponseDTO.builder()
                .status(HttpStatus.ACCEPTED)
                .build();

        try {
            role.setRole(ROLE_USER);
            roleMapper.deleteRoleUser(role);

            responseDto.setStatus(HttpStatus.OK);
            responseDto.setMsg(SUCCESS_MSG_DELETE_ROLE_USER);

        } catch (Exception e) {
            responseDto.setMsg(EXCEPTION_MSG_DURING_DELETER);
            e.printStackTrace();
        }

        return responseDto;
    }
    */