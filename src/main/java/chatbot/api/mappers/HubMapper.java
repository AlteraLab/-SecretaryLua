package chatbot.api.mappers;

import chatbot.api.hub.domain.HubInfoDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Mapper
@Repository
public interface HubMapper {

    Optional<HubInfoDto> getUserHub(Long userId);

    void save(HubInfoDto hub);
}
