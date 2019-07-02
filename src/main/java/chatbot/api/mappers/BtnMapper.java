package chatbot.api.mappers;

import chatbot.api.buildcode.domain.BtnDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Mapper
@Repository
public interface BtnMapper {

    ArrayList<BtnDTO> getBtnsByBoxIdAndAuthKey(@Param("boxId") Long boxId, @Param("authKey") String authKey);
}
