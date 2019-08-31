package chatbot.api.role.services;

import chatbot.api.common.domain.ResponseDTO;
import chatbot.api.mappers.RoleMapper;
import chatbot.api.role.domain.RoleDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static chatbot.api.role.utils.RoleConstants.EXCEPTION_MSG_DURING_GETTER;
import static chatbot.api.role.utils.RoleConstants.FAIL_MSG_GET_ROLE_BECAUSE_NO_EXIST;
import static chatbot.api.role.utils.RoleConstants.SUCCESS_MSG_GET_ROLES_USER;

@Service
@Slf4j
@AllArgsConstructor
public class RoleSelectService {

    private RoleMapper roleMapper;



    public ResponseDTO getRoles(Long hubId) {

        ResponseDTO responseDto = ResponseDTO.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();

        try {
            List<RoleDTO> roleInfoList = roleMapper.getRolesInfoByHubId(hubId);
            if(roleInfoList == null) {
                responseDto.setMsg(FAIL_MSG_GET_ROLE_BECAUSE_NO_EXIST);
                responseDto.setStatus(HttpStatus.NO_CONTENT);
                return responseDto;
            }

            responseDto.setStatus(HttpStatus.OK);
            responseDto.setMsg(SUCCESS_MSG_GET_ROLES_USER);
            responseDto.setData(roleInfoList);

            log.info(roleInfoList.toString());

        } catch (Exception e) {
            responseDto.setMsg(EXCEPTION_MSG_DURING_GETTER);
            e.printStackTrace();

        } finally {
            return responseDto;
        }
    }
}
