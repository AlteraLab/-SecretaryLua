package chatbot.api.mappers;

import chatbot.api.buildcode.domain.BtnDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Mapper
@Repository
public interface BtnMapper {

    ArrayList<BtnDto> getBtnsByBoxIdAndAuthKey(@Param("boxId") Long boxId, @Param("authKey") String authKey);
}
