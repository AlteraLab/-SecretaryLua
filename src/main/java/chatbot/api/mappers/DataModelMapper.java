package chatbot.api.mappers;

import chatbot.api.dev.domain.EventVO;
import chatbot.api.textbox.domain.textboxdata.DataModelDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Mapper
@Repository
public interface DataModelMapper {

    ArrayList<DataModelDTO> getDataModelsByHrdwrId(@Param("hrdwrId") Long hrdwrId);

    ArrayList<String> getSensingKeySet(@Param("hrdwrId") Long hrdwrId, @Param("modType") Character modType);

    ArrayList<String> getDevInfoKeySet(@Param("hrdwrId") Long hrdwrId, @Param("modType") Character modType);

    ArrayList<EventVO> getAllEvents(@Param("hrdwrId") Long hrdwrId);
}
