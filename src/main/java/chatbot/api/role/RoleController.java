package chatbot.api.role;


import chatbot.api.common.domain.ResponseDto;
import chatbot.api.common.security.UserPrincipal;
import chatbot.api.role.services.RoleRegister;
import chatbot.api.user.domain.UserRegisterVo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@NoArgsConstructor
public class RoleController {

    private RoleRegister roleRegister;

    // 관리자가 새로운 그룹 유저를 추가해주기 위해 호출하는 메소드
    @PostMapping("/hubUser")
    public ResponseDto addRole(@AuthenticationPrincipal UserPrincipal userPrincipal,
                               @RequestBody UserRegisterVo userRegisterVo) {

        return roleRegister.register(userRegisterVo, userPrincipal.getId());  // (hubId / email / adminId)

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
