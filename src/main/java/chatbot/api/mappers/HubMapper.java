package chatbot.api.mappers;

import chatbot.api.skillHub.domain.HubInfoDto;
import chatbot.api.skillHub.domain.HubTableVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface HubMapper {

    // 허브 삽입
    void save(HubInfoDto hub);


    // 인자로 넣은 userId가 사용할 수 있는 모든 허브 검색
    Optional<HubInfoDto> getUserHub(Long userId);


    // adminId를 가지고 허브info 조회
    ArrayList<HubTableVo> getHubInfoByAdminId(Long adminId);


    HubInfoDto getHubInfo(Long hubSeq);


    // delete hub by hubSeq
    void deleteHub(Long hubSeq);


    // Schedule query : implicit Delete Hub
    void implicitDeleteHub(@Param("expireDate") Date expireDate);
}