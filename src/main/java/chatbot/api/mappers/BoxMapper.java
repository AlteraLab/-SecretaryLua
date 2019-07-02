package chatbot.api.mappers;

import chatbot.api.buildcode.domain.BoxDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BoxMapper {

    BoxDTO getEntryBoxByAuthKey(@Param("authKey") String authKey, @Param("boxId") Long boxId);

    BoxDTO getBoxByBoxIdAndAuthKey(@Param("boxId") Long boxId, @Param("authKey") String authKey);
}
