package chatbot.api.skillHub.services;

import chatbot.api.common.domain.ResponseDto;
import chatbot.api.common.security.UserPrincipal;
import chatbot.api.hub.domain.HubInfoDto;
import chatbot.api.mappers.SkillHubMapper;
import chatbot.api.mappers.SkillHubUserMapper;
import chatbot.api.skillHub.domain.SkillHubInfoDto;
import chatbot.api.skillHub.domain.SkillHubUserInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class SkillHubRegister {

    @Autowired
    private SkillHubMapper skillHubMapper;

    @Autowired
    private SkillHubUserMapper skillHubUserMapper;

    @Transactional
    public void register(SkillHubInfoDto hub, SkillHubUserInfoDto role) {

        // 허브 데이터 저장
        skillHubMapper.save(hub);
        System.out.println("after : " + hub);

        // 방금 저장한 hubSeq를 알아낸다.
        Long hubSeq = hub.getHubSeq();
        System.out.println("hubSeq : " + hubSeq);

        // 허브id set
        role.setHubSeq(hubSeq);

        // hub_user 저장
        skillHubUserMapper.save(role);
    }
}
