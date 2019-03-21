package chatbot.api.role;


import chatbot.api.common.domain.ResponseDto;
import chatbot.api.common.security.UserPrincipal;
import chatbot.api.mappers.HubMapper;
import chatbot.api.mappers.UserMapper;
import chatbot.api.role.services.RoleRegister;
import chatbot.api.skillHub.domain.HubInfoDto;
import chatbot.api.user.domain.UserRegisterVo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@NoArgsConstructor
public class RoleController {

    @Autowired
    private HubMapper hubMapper;

    private RoleRegister roleRegister;

    // 관리자가 새로운 그룹 유저를 추가해주기 위해 호출하는 메소드
    // 일단은 invite 메시지 전송 기능을 생각하지 않고 기능 구현
    @PostMapping("/user")
    public ResponseDto addRole(@AuthenticationPrincipal UserPrincipal userPrincipal,
                               @RequestBody UserRegisterVo userRegisterVo) {

        // 받는 데이터, 사용자 이메일
        // 요청 응답 status ok || fail
        System.out.println(userRegisterVo);
        HubInfoDto hub = hubMapper.getHubInfo(userRegisterVo.getHubId());
        return ResponseDto.builder().build();
        //return roleRegister.register(userRegisterVo, userPrincipal.getId());  // (hubId / email / adminId)
        //return roleRegister.register(userRegisterVo, new Long(5));  // (hubId / email / adminId)

        /* 이코드를 위의 return 문으로 수정, 혹시 위의 코드가 에러가 발생할까봐 남겨뒀음
        try {
            responseDto = skillHubUserRegister.register(userRegisterVo, userPrincipal.getId());  // (hubId / email / adminId)
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return responseDto;
        }*/
    }

}
