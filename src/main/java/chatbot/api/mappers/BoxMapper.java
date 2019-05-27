package chatbot.api.mappers;

import chatbot.api.buildcode.domain.BoxDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BoxMapper {

    BoxDto getEntryBoxByAuthKey(@Param("authKey") String authKey, @Param("boxId") Long boxId);

    BoxDto getBoxByBoxIdAndAuthKey(@Param("boxId") Long boxId, @Param("authKey") String authKey);
}
