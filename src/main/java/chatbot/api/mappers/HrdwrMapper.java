package chatbot.api.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface HrdwrMapper {

    String getAuthKeyByUserDefinedName(@Param("userDefinedName") String userDefinedName);
}
