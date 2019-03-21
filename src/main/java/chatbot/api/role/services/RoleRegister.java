package chatbot.api.role.services;

import chatbot.api.common.domain.ResponseDto;
import chatbot.api.mappers.HubMapper;
import chatbot.api.mappers.RoleMapper;
import chatbot.api.mappers.UserMapper;
import chatbot.api.skillHub.domain.HubInfoDto;
import chatbot.api.role.domain.RoleDto;
import chatbot.api.user.domain.UserInfoDto;
import chatbot.api.user.domain.UserRegisterVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static chatbot.api.role.utils.RoleConstants.*;
import static chatbot.api.skillHub.utils.HubConstants.*;
import static chatbot.api.user.utils.UserConstants.FAIL_MSG_SELECT_BY_EMAIL;


@Slf4j
@Service
@AllArgsConstructor
public class RoleRegister {

    //@Autowired
    UserMapper userMapper;

    //@Autowired
    HubMapper hubMapper;

    //@Autowired
    RoleMapper roleMapper;



    // userRegisterVo(hubId, userId), userprincipal.getId()
    public ResponseDto register(UserRegisterVo userRegisterVo, Long adminId) {


        ResponseDto responseDto = new ResponseDto().builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();

        try {
            responseDto.setMsg(FAIL_MSG_NO_EXIST_HUB);
            HubInfoDto hub = hubMapper.getHubInfo(userRegisterVo.getHubId());
            if(hub == null)                  return responseDto;

            responseDto.setMsg(FAIL_MSG_NO_ADMIN);
            if(adminId != hub.getAdminSeq()) return responseDto;

            // userSeq를 가져오는 코드를 추가해야 할 듯
            responseDto.setMsg(FAIL_MSG_SELECT_BY_EMAIL);
            UserInfoDto user = userMapper.getUserByEmail(userRegisterVo.getEmail());
            if(user == null)                 return responseDto;

            responseDto.setMsg(FAIL_MSG_ALREADY_ROLE_USER);
            RoleDto role = roleMapper.getRoleInfo(userRegisterVo.getHubId(), user.getUserId());
            if(role != null)                 return responseDto;


            // finish check list


            // init
            role = new RoleDto().builder()
                    .hubSeq(userRegisterVo.getHubId())
                    .userSeq(user.getUserId())
                    .role(ROLE_USER)
                    .build();


            responseDto.setMsg(FAIL_MSG_REGIST_ROLE_INTO_ROLE_TABLE);
            roleMapper.save(role);

            responseDto.setMsg(SUCCESS_MSG_ADD_ROLL_USER);
            responseDto.setStatus(HttpStatus.CREATED);
            responseDto.setData(role);

        } catch (Exception e) {
            log.info(EXCEPTION_MSG_DURING_REGISTER);
            e.printStackTrace();

        }

        return responseDto;
    }
}