package chatbot.api.skillHub.services;

import chatbot.api.common.domain.ResponseDto;
import chatbot.api.mappers.HubMapper;
import chatbot.api.mappers.RoleMapper;
import chatbot.api.mappers.UserMapper;
import chatbot.api.skillHub.domain.HubInfoDto;
import chatbot.api.role.domain.RoleDto;
import chatbot.api.user.domain.UserInfoDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static chatbot.api.role.utils.RoleConstants.EXCEPTION_MSG_DURING_REGISTER;
import static chatbot.api.role.utils.RoleConstants.FAIL_MSG_REGIST_ROLE_INTO_ROLE_TABLE;
import static chatbot.api.skillHub.utils.HubConstants.*;

@Slf4j
@Service
@AllArgsConstructor
public class HubRegister {

    private HubMapper hubMapper;

    private RoleMapper roleMapper;

    private UserMapper userMapper;



    @Transactional
    public ResponseDto register(HubInfoDto hub, RoleDto role) {


        ResponseDto responseDto = new ResponseDto().builder()
                                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .build();

        try {
            // 관리자로 등록하려는 사용자의 id는 users 테이블에 있는 데이터인가?
            UserInfoDto user = userMapper.getUserByUserId(role.getUserSeq());
            if(user == null) {
                responseDto.setMsg(FAIL_MSG_NO_EXIST_USER_FROM_TABLE);
                responseDto.setStatus(HttpStatus.ACCEPTED);

                return responseDto;
            }

            // hub 저장
            responseDto.setMsg(FAIL_MSG_REGIST_HUB_INTO_HUB_TABLE);
            hubMapper.save(hub);

            // role 객체의 hubSeq 멤버를 set
            role.setHubSeq(hub.getHubSeq());

            // role 저장
            responseDto.setMsg(FAIL_MSG_REGIST_ROLE_INTO_ROLE_TABLE);
            roleMapper.save(role);

            responseDto.setMsg(SUCCESS_MSG_REGIST_INTO_HUB_AND_ROLL);
            responseDto.setStatus(HttpStatus.CREATED);
            responseDto.setData(role);

        } catch (Exception e) {
            log.info(EXCEPTION_MSG_DURING_REGISTER);
            e.printStackTrace();

        } finally {
            return responseDto;
        }

    }
}
