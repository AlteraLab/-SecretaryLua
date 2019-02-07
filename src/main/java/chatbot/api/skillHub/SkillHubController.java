package chatbot.api.skillHub;

import ch.qos.logback.core.net.SyslogOutputStream;
import chatbot.api.common.domain.ResponseDto;
import chatbot.api.skillHub.domain.SkillHubInfoDto;
import chatbot.api.skillHub.domain.SkillHubUserInfoDto;
import chatbot.api.skillHub.domain.SkillHubVo;
import chatbot.api.skillHub.services.SkillHubRegister;
import org.apache.ibatis.annotations.Param;
import org.json.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import javax.xml.ws.Response;
import java.sql.SQLException;

@RestController
public class SkillHubController {

    @Autowired
    SkillHubRegister skillHubRegister;

    // 허브 최초 등록 기능
    @PostMapping("/skillHub")
    public ResponseDto createHub(
            @RequestBody SkillHubVo skillHubVo) {
        // @AuthenticationPrincipal UserPrincipal userPrincipal, 원래 들어가야하는 인자

        // 허브인포dto
        SkillHubInfoDto hub = SkillHubInfoDto.builder()
                .hubName(null)      // 일단 이렇게
                .adminSeq(skillHubVo.getUserId())
                .currentIp(skillHubVo.getExternalIp())
                .port(skillHubVo.getPort())
                .beforeIp(null)
                .connectionModuleNum(0)
                .commandExecutionStat(false)
                .build();
        System.out.println("hubName : " +  hub.getHubName());
        System.out.println("currentIp : " +  hub.getCurrentIp());
        System.out.println("port : " +  hub.getPort());
        System.out.println("beforeIp : " +  hub.getBeforeIp());
        System.out.println("connectionModuleNum : " +  hub.getConnectionModuleNum());

        // 허브유저인포dto, hubSeq 아직 set 안해줌
        SkillHubUserInfoDto role = SkillHubUserInfoDto.builder()
                .userSeq(skillHubVo.getUserId())    // userPrincipal.getId();
                .role("ROLE_ADMIN")
                .build();

        try {
            skillHubRegister.register(hub, role);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.builder()
                    .msg("fail")
                    .status(HttpStatus.OK)
                    .build();
        }

        return ResponseDto.builder()
                .msg("success")
                .status(HttpStatus.OK)
                .build();
    }
}
