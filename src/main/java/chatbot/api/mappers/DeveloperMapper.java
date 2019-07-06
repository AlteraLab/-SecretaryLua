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

    DvlpDevDTO getDevice(@Param("hrdwrId") Long devId);

    // 데이터 삭제, derivation -> btn -> box -> device
    void deleteDerivations(@Param("hrdwrId") Long devId);

    void deleteButtons(@Param("hrdwrId") Long devId);

    void deleteTextBoxes(@Param("hrdwrId") Long devId);

    void deleteDevice(@Param("hrdwrId") Long devId);


    // 다건 데이터 삽입, device -> box -> btn -> derivation
    void insertDevice(
            @Param("dvlpDevDTO")
            DvlpDevDTO dvlpDevDTO
    );

    void insertTextBoxes(
            @Param("dvlpBoxDTOList")
            List<DvlpBoxDTO> dvlpBoxDTOList,
            @Param("hrdwrId")
            Long devId
    );

    void insertButtons(
            @Param("dvlpButtonDTOList")
            List<DvlpButtonDTO> dvlpButtonDTOList,
            @Param("hrdwrId")
            Long devId
    );

    void insertDerivations(
            @Param("dvlpDerivationDTOList")
            List<DvlpDerivationDTO> dvlpDerivationDTOList,
            @Param("hrdwrId")
            Long devId
    );
}