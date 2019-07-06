package chatbot.api.mappers;

import chatbot.api.textbox.domain.textboxdata.DerivationDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Mapper
@Repository
public interface DerivationMapper {

    ArrayList<DerivationDTO> getDerivationsByHrdwrId(@Param("hrdwrId") Long hrdwrId);
}