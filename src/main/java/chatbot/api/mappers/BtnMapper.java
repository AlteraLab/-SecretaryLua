package chatbot.api.mappers;

import chatbot.api.textbox.domain.textboxdata.BtnDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Mapper
@Repository
public interface BtnMapper {

    ArrayList<BtnDTO> getBtnsByHrdwrId(@Param("hrdwrId") Long hrdwrId);
}
