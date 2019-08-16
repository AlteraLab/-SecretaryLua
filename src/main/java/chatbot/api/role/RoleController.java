package chatbot.api.role;


import chatbot.api.common.domain.ResponseDTO;
import chatbot.api.common.security.UserPrincipal;
import chatbot.api.mappers.HubMapper;
import chatbot.api.mappers.RoleMapper;
import chatbot.api.mappers.UserMapper;
import chatbot.api.role.domain.RoleDTO;
import chatbot.api.role.services.RoleCheckService;
import chatbot.api.role.services.RoleDeleteService;
import chatbot.api.role.services.RoleSelectService;
import chatbot.api.role.services.RoleRegisterService;
import chatbot.api.skillhub.domain.HubInfoDTO;
import chatbot.api.user.domain.UserInfoDto;
import chatbot.api.user.domain.UserRegisterVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;

@RestController
@Slf4j
public class RoleController {

    @Autowired
    private RoleCheckService roleCheckService;

    @Autowired
    private RoleRegisterService roleRegisterService;

    @Autowired
    private RoleDeleteService roleDeleteService;

    @Autowired
    private RoleSelectService roleSelectService;

    @Autowired
    private HubMapper hubMapper;

    @Autowired
    private UserMapper userMapper;

    // 관리자가 새로운 유저 검색 및 추가, by emailㄴ
    @PostMapping("/{hubIp}/{userEmail:.+}")
    public ResponseDTO addRoleToHubGroup(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                         @PathVariable("userEmail") String userEmail,
                                         @PathVariable("hubIp") String hubIp) {
        // 포트도 보내야 하나??
        HubInfoDTO hub = hubMapper.getHubInfoByIp(hubIp);
        UserInfoDto user = userMapper.getUserByEmail(userEmail);
        String failMsg = roleCheckService.checkValidForRegistUser(
                userPrincipal.getId(),
                user,
                hub
        );
        log.info("Fail Msg -> " + failMsg);
        if(failMsg != null) {
            return ResponseDTO.builder()
                    .status(HttpStatus.OK)
                    .msg(failMsg)
                    .build();
        }
        return roleRegisterService.registerForUser(user, hub);
    }

    // 관리자가 새로운 그룹 유저를 추가해주기 위해 호출하는 메소드
    // 일단은 invite 메시지 전송 기능을 생각하지 않고 기능 구현
    @PostMapping("/role")
    public ResponseDTO addRole(//@PathVariable("userId") Long userId,
                               @AuthenticationPrincipal UserPrincipal userPrincipal,
                               @RequestBody UserRegisterVO userRegisterVo) {
        log.info(userRegisterVo.toString());

        return roleRegisterService.register(userRegisterVo, userPrincipal.getId());
        //return roleRegister.register(userRegisterVo, userId);
    }



    // 관리자가 허브에 대한 ROLE_USER 권한을 가진 사용자를 삭제하는 기능
    @DeleteMapping("/role")
    public ResponseDTO deleteRoleUser(//@PathVariable("adminId") Long adminId,
                                      @AuthenticationPrincipal UserPrincipal userPrincipal,
                                      @RequestBody UserRegisterVO userRegisterVo) {

        log.info(userRegisterVo.toString());
        return roleDeleteService.deleterRoleUserByAdmin(userRegisterVo, userPrincipal.getId());
        //return roleDeleter.deleterRoleUserByAdmin(userRegisterVo, adminId);
    }



    // 허브 사용자 관리 페이지로 왔을때 해당 허브 사용자들의 데이터를 화면에 배치해야함
    // 허브에 대해 권한을 가진 유저들에 대한 정보를 반환하는 기능
    @GetMapping("/hub/{hubId}/roles")
    public ResponseDTO getRoles(@PathVariable("hubId") Long hubId) {
        return roleSelectService.getRoles(hubId);
    }
}
