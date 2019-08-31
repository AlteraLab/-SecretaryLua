package chatbot.api.mappers;

import chatbot.api.textbox.domain.textboxdata.StateRuleDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Mapper
@Repository
public interface StateRuleMapper {

    ArrayList<StateRuleDTO> getStateRulesByHrdwrId(@Param("hrdwrId") Long hrdwrId);
}
