package chatbot.api.mappers;

import chatbot.api.textbox.domain.textboxdata.BoxDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Mapper
@Repository
public interface BoxMapper {

    ArrayList<BoxDTO> getBoxsByHrdwrId(@Param("hrdwrId") Long hrdwrId);
}
