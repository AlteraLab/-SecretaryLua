package chatbot.api.mappers;

import chatbot.api.build.domain.BoxDto;
import chatbot.api.build.domain.HrdwrDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface HrdwrMapper {

    String getAuthKeyByUserDefinedName(@Param("userDefinedName") String userDefinedName);
}
