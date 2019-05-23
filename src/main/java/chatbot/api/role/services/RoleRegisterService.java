package chatbot.api.role.services;

import chatbot.api.common.domain.ResponseDto;
import chatbot.api.mappers.HubMapper;
import chatbot.api.mappers.RoleMapper;
import chatbot.api.mappers.UserMapper;
import chatbot.api.skillhub.domain.HubInfoDto;
import chatbot.api.role.domain.RoleDto;
import chatbot.api.user.domain.UserInfoDto;
import chatbot.api.user.domain.UserRegisterVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static chatbot.api.role.utils.RoleConstants.*;
import static chatbot.api.skillhub.utils.HubConstants.*;
import static chatbot.api.user.utils.UserConstants.FAIL_MSG_SELECT_BY_EMAIL;



@Service
@Slf4j
@AllArgsConstructor
public class RoleRegisterService {

    private UserMapper userMapper;

    private HubMapper hubMapper;

    private RoleMapper roleMapper;



    // userRegisterVo(hubId, userId), userprincipal.getId()
    public ResponseDto register(UserRegisterVo userRegisterVo, Long adminId) {


        ResponseDto responseDto = new ResponseDto().builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();

        try {
            responseDto.setMsg(FAIL_MSG_NO_EXIST_HUB);
            HubInfoDto hub = hubMapper.getHubInfo(userRegisterVo.getHubId());
            if(hub == null)                  return responseDto;
            log.info(hub.toString());

            responseDto.setMsg(FAIL_MSG_NO_ADMIN);
            if(adminId != hub.getAdminId()) return responseDto;

            responseDto.setMsg(FAIL_MSG_SELECT_BY_EMAIL);
            UserInfoDto user = userMapper.getUserByEmail(userRegisterVo.getEmail());
            if(user == null)                 return responseDto;
            log.info(user.toString());

            responseDto.setMsg(FAIL_MSG_ALREADY_ROLE_USER);
            RoleDto role = roleMapper.getRoleInfo(userRegisterVo.getHubId(), user.getUserId());
            if(role != null)                 return responseDto;


            // finish check list


            responseDto.setMsg(FAIL_MSG_REGIST_ROLE_INTO_ROLE_TABLE);
            roleMapper.save(role);

            responseDto.setMsg(SUCCESS_MSG_ADD_ROLE_USER);
            responseDto.setStatus(HttpStatus.CREATED);
            responseDto.setData(new Object(){
                public RoleDto roleDto = RoleDto.builder()
                        .hubId(userRegisterVo.getHubId())
                        .userId(user.getUserId())
                        .role(ROLE_USER)
                        .build();
            });
        } catch (Exception e) {
            log.info(EXCEPTION_MSG_DURING_REGISTER);
            e.printStackTrace();
        }

        return responseDto;
    }
}