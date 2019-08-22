package chatbot.api.mappers;

import chatbot.api.textbox.domain.textboxdata.DataModelDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Mapper
@Repository
public interface DataModelMapper {

    ArrayList<DataModelDTO> getDataModelsByHrdwrId(@Param("hrdwrId") Long hrdwrId);

    ArrayList<DataModelDTO> getDataModelsForTableByHrdwrId(@Param("hrdwrId") Long hrdwrId);
}
