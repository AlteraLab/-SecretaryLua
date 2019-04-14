package chatbot.api.mappers;

import chatbot.api.skillHub.domain.HubInfoDto;
import chatbot.api.skillHub.domain.HubVo;
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
    ArrayList<HubInfoDto> getHubInfoByAdminId(Long adminId);


    // 사용자가 사용할 수 있는 허브들중에서 사용자가 요청한 장치 타입이 부착된 허브들을 조회
    ArrayList<HubInfoDto> getHubInfoByDevCategory(@Param("userId") Long userId,
                                                  @Param("devCategory") String devCategory);


    // 메인 페이지에서 보여줄 허브 목록에 대한 데이터를 가져온다.
    //ArrayList<HubVo> getHubsInfoByUserId(Long userId);
    ArrayList<HubVo> getHubsInfoByUserId(Long userId);


    // get HubInfo
    HubInfoDto getHubInfo(Long hubSeq);


    HubInfoDto getHubInfoByMacAddr(@Param("macAddr") String macAddr);


    ArrayList<HubInfoDto> getUserHubsByUserId(@Param("userId") Long userId);

    // delete hub by hubSeq
    void deleteHub(Long hubSeq);


    // Schedule query : implicit Delete Hub
    void implicitDeleteHub(@Param("expireDate") Date expireDate);


    // edit hub info
    void editHub(@Param("macAddr") String macAddr,
                 @Param("name") String hubName,
                 @Param("searchId") String searchId,
                 @Param("desc") String hubDescription,
                 @Param("externalIp") String externalIp,
                 @Param("externalPort") int externalPort,
                 @Param("beforeIp") String beforeIp);
}
