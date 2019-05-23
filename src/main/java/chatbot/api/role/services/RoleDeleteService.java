package chatbot.api.role.services;

import chatbot.api.common.domain.ResponseDto;
import chatbot.api.mappers.HubMapper;
import chatbot.api.mappers.RoleMapper;
import chatbot.api.role.domain.RoleDto;
import chatbot.api.skillhub.domain.HubInfoDto;
import chatbot.api.user.domain.UserRegisterVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static chatbot.api.role.utils.RoleConstants.*;
import static chatbot.api.skillhub.utils.HubConstants.ROLE_USER;

@Service
@Slf4j
@AllArgsConstructor
public class RoleDeleteService {

    private RoleMapper roleMapper;

    private HubMapper hubMapper;



    // 관리자가 허브에 대한 ROLE_USESR 권한을 가진 사용자를 삭제하는 기능
    public ResponseDto deleterRoleUserByAdmin(UserRegisterVo userRegisterVo, Long adminId) {

        ResponseDto responseDto = ResponseDto.builder()
                .data(null)
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();

        try {
            // 1. userPrincipal.getId()가 허브에 대해서 admin인지 검증
            HubInfoDto hub = hubMapper.getHubInfo(userRegisterVo.getHubId());
            if(hub == null)       return responseDto;

            log.info(hub.toString());

            if(!adminId.equals(hub.getAdminId())) {
                responseDto.setMsg(FAIL_MSG_NO_ADMIN);
                return responseDto;
            }

            // 2. 해당 유저가 허브를 사용가능한지 확인
            RoleDto roleUser = roleMapper.getRoleInfo(userRegisterVo.getHubId(), userRegisterVo.getUserId());
            if(roleUser == null) {
                responseDto.setMsg(FAIL_MSG_NO_ROLE_USER);
                responseDto.setStatus(HttpStatus.NO_CONTENT);
                return responseDto;
            }

            log.info(roleUser.toString());

            // 3. 해당 유저가 ROLE_USER 이면, 삭제
            if(roleUser.getRole().equals(ROLE_USER)) {
                roleMapper.deleteRoleUser(roleUser);
                responseDto.setMsg(SUCCESS_MSG_DELETE_ROLE_USER);
                responseDto.setStatus(HttpStatus.OK);
            }

        } catch (Exception e) {
            log.info(EXCEPTION_MSG_DURING_DELETER);
            e.printStackTrace();

        } finally {
            return responseDto;
        }
    }
}
