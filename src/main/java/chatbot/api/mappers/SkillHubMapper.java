package chatbot.api.mappers;

import chatbot.api.hub.domain.HubInfoDto;
import chatbot.api.skillHub.domain.SkillHubInfoDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Mapper
@Repository
public interface SkillHubMapper {

    // 허브 삽입
    void save(SkillHubInfoDto hub);

    // 허브 시퀀스 조회, 나중에
    Long getHubSeq(SkillHubInfoDto hub);

    // 허브 getRole, 나중에
    Long getRole(SkillHubInfoDto hub);
}
