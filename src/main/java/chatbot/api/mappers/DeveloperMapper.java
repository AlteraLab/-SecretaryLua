package chatbot.api.mappers;

import chatbot.api.developer.domain.DvlpBoxDTO;
import chatbot.api.developer.domain.DvlpButtonDTO;
import chatbot.api.developer.domain.DvlpDerivationDTO;
import chatbot.api.developer.domain.DvlpDevDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DeveloperMapper {

    DvlpDevDTO getDevice(@Param("devId") Long devId);

    // 데이터 삭제, derivation -> btn -> box -> device
    void deleteDerivations(@Param("devId") Long devId);

    void deleteButtons(@Param("devId") Long devId);

    void deleteTextBoxes(@Param("devId") Long devId);

    void deleteDevice(@Param("devId") Long devId);


    // 다건 데이터 삽입, device -> box -> btn -> derivation
    void insertDevice(
            @Param("dvlpDevDTO")
            DvlpDevDTO dvlpDevDTO
    );

    void insertTextBoxes(
            @Param("dvlpBoxDTOList")
            List<DvlpBoxDTO> dvlpBoxDTOList,
            @Param("devId")
            Long devId
    );

    void insertButtons(
            @Param("dvlpButtonDTOList")
            List<DvlpButtonDTO> dvlpButtonDTOList,
            @Param("devId")
            Long devId
    );

    void insertDerivations(
            @Param("dvlpDerivationDTOList")
            List<DvlpDerivationDTO> dvlpDerivationDTOList,
            @Param("devId")
            Long devId
    );
}