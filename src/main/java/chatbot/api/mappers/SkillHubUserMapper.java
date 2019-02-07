package chatbot.api.mappers;

import chatbot.api.hub.domain.HubInfoDto;
import chatbot.api.skillHub.domain.SkillHubUserInfoDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Mapper
@Repository
public interface SkillHubUserMapper {
    void save(SkillHubUserInfoDto hubUser);
}
