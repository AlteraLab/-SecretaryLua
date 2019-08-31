package chatbot.api.role.services;

import chatbot.api.mappers.RoleMapper;
import chatbot.api.role.domain.RoleDTO;
import chatbot.api.skillhub.domain.HubInfoDTO;
import chatbot.api.user.domain.UserInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RoleCheckService {

    @Autowired
    private RoleMapper roleMapper;

    public String checkValidForRegistUser(Long adminId, UserInfoDto user, HubInfoDTO hub) {
        String msg = new String();
        if(hub == null) {
            return msg = "존재하지 않는 허브 입니다.";
        } else if(!adminId.equals(hub.getAdminId())) {
            return msg = "허브 관리자가 아닙니다.";
        } else if(user == null) {
            return msg = "존재하지 않는 유저 입니다.";
        } else if(roleMapper.getRoleInfo(hub.getHubId(), user.getUserId()) != null) {
            return msg = "이미 소속된 유저 입니다.";
        }
        return null;
    }
}
