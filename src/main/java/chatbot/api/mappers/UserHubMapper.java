package chatbot.api.mappers;

import chatbot.api.hub.domain.UserHubDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserHubMapper {

    void save(UserHubDto role);
}
