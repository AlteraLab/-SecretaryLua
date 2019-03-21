package chatbot.api.mappers;

import chatbot.api.skillHub.domain.HubInfoDto;
import chatbot.api.skillHub.domain.HubTableVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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

    // 딴건 잘되나?
    HubInfoDto getHubInfo(Long hubSeq);


    // delete hub by hubSeq
    void deleteHub(Long hubSeq);


    // Schedule query : implicit Delete Hub
    void implicitDeleteHub(@Param("expireDate") Date expireDate);


    // edit hub info about (external ip / port), 9internal ip / port)
    void editHubAboutIpAndPort(@Param("hubSeq") Long hubSeq,
                               @Param("exIp") String exIp,
                               @Param("inIp") String inIp,
                               @Param("exPort") int exPort,
                               @Param("inPort") int inPort);


}
