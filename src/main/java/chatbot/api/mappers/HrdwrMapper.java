package chatbot.api.mappers;

import chatbot.api.textbox.domain.path.HrdwrDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface HrdwrMapper {

    HrdwrDTO getHrdwrByAuthKey(@Param("authKey") String authKey);
}
