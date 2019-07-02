package chatbot.api.mappers;

import chatbot.api.hublog.domain.HubLogDTO;
import chatbot.api.hublog.domain.HubLogVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface HubLogMapper {

    void saveHubLog(HubLogDTO hubLogDto);

    List<HubLogVO> getHubLog(@Param("hubId") Long hubId);
}
