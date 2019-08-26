package chatbot.api.mappers;

import chatbot.api.developer.domain.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DeveloperMapper {

    DvlpDevDTO getDevice(@Param("hrdwrId") Long devId);

    // 데이터 삭제, derivation -> (btn / rule / event) -> (box / data_model) -> device
    void deleteDerivations(@Param("hrdwrId") Long devId);

    void deleteButtons(@Param("hrdwrId") Long devId);

    void deleteRules(@Param("hrdwrId") Long devId);

    void deleteEvents(@Param("hrdwrId") Long devId);

    void deleteTextBoxes(@Param("hrdwrId") Long devId);

    void deleteDataModels(@Param("hrdwrId") Long devId);

    void deleteDevice(@Param("hrdwrId") Long devId);


    // 다건 데이터 삽입, device -> (box / data_model) -> (btn / rule / event) -> derivation
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

    // 데이터 모델
    void insertDataModels(
            @Param("dvlpDataModelDTOList")
            List<DvlpDataModelDTO> dvlpDataModelDTOList,
            @Param("hrdwrId")
            Long devId
    );

    void insertButtons(
            @Param("dvlpButtonDTOList")
            List<DvlpButtonDTO> dvlpButtonDTOList,
            @Param("hrdwrId")
            Long devId
    );

    // 룰
    void insertRules(
            @Param("dvlpStateRuleDTOList")
            List<DvlpStateRuleDTO> dvlpStateRuleDTOList,
            @Param("hrdwrId")
            Long devId
    );

    // 이벤트
    void insertEvents(
        @Param("dvlpEventDTOList")
        List<DvlpEventDTO> dvlpEventDTOList,
        @Param("hrdwrId")
        Long devId
    );

    // 알림 박스 (이벤트 하위 테이블)
    void insertNotifyBoxs(
        @Param("dvlpNotifyBoxDTOList")
        List<DvlpNotifyBoxDTO> dvlpNotifyBoxDTOList
    );

    // 서드 파티 (이벤트 하위 테이블)
    void insertThirdServers(
        @Param("dvlpThirdServerDTOList")
        List<DvlpThirdServerDTO> dvlpThirdServerDTOList
    );

    // 제어 (이벤트 하위 테이블)
    void insertControls(
        @Param("dvlpControlDTOList")
        List<DvlpControlDTO> dvlpControlDTOList
    );

    void insertDerivations(
            @Param("dvlpDerivationDTOList")
            List<DvlpDerivationDTO> dvlpDerivationDTOList,
            @Param("hrdwrId")
            Long devId
    );
}