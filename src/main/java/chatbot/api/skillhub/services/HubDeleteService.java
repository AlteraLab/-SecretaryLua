package chatbot.api.skillhub.services;

import chatbot.api.common.domain.ResponseDto;
import chatbot.api.mappers.HubMapper;
import chatbot.api.mappers.RoleMapper;
import chatbot.api.role.domain.RoleDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static chatbot.api.role.utils.RoleConstants.SUCCESS_MSG_DELETE_ROLE_USER;
import static chatbot.api.skillhub.utils.HubConstants.*;

@Service
@Slf4j
@AllArgsConstructor
public class HubDeleteService {

    private HubMapper hubMapper;

    private RoleMapper roleMapper;



    // admin이 해당 허브에 대한 정보 모두 삭제
    // 나중에 허브에 대한 모듈 테이블이 자식 테이블로 생성될 시 자식 테이블으 모듈들도 제거해주는 코드 작성
    @Transactional
    public ResponseDto explicitDeleterByAdmin(RoleDto role) {

        ResponseDto responseDto = new ResponseDto().builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();


        try {

            // 허브에 종속된 모듈 제거 메서드 실행
            // ...

            // skillHubUserMapper.deleterHubUser
            responseDto.setMsg(FAIL_MSG_EXPLICIT_DEL_AT_QUERY_ABOUT_ROLL);
            roleMapper.deleteAllRoleByAdmin(role);

            // skillHubMapper.deleterUser
            responseDto.setMsg(FAIL_MSG_EXPLICIT_DEL_AT_QUERY_ABOUT_HUB);
            hubMapper.deleteHub(role.getHubId());

            responseDto.setMsg(SUCCESS_MSG_EXPLICIT_DEL);
            responseDto.setStatus(HttpStatus.OK);

        } catch (Exception e) {
            log.debug(EXCEPTION_MSG_DURING_DELETER);
            e.printStackTrace();

        } finally {
            return responseDto;
        }
    }



    // 허브에 대한 일반 유저가 스스로 허브에 대한 권한을 스스로 ㄹ제거
    public ResponseDto explicitDeleterByUser(RoleDto role) {

        ResponseDto responseDto = ResponseDto.builder()
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
}