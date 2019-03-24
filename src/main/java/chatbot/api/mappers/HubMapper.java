package chatbot.api.mappers;

import chatbot.api.skillHub.domain.HubInfoDto;
import chatbot.api.skillHub.domain.HubTableVo;
import chatbot.api.skillHub.domain.HubsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

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


    // 메인 페이지에서 보여줄 허브 목록에 대한 데이터를 가져온다.
    ArrayList<HubsVo> getHubsInfoByUserId(Long userId);


    // get HubInfo
    HubInfoDto getHubInfo(Long hubSeq);


    // delete hub by hubSeq
    void deleteHub(Long hubSeq);


    // Schedule query : implicit Delete Hub
    void implicitDeleteHub(@Param("expireDate") Date expireDate);


    // edit hub info about (external ip / port), (internal ip / port)
    void editHubAboutIpAndPort(@Param("hubSeq") Long hubSeq,
                               @Param("exIp") String exIp,
                               @Param("inIp") String inIp,
                               @Param("exPort") int exPort,
                               @Param("inPort") int inPort);
}
