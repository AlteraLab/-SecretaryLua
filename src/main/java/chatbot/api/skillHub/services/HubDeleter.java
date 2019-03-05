package chatbot.api.skillHub.services;

import chatbot.api.common.domain.ResponseDto;
import chatbot.api.mappers.HubMapper;
import chatbot.api.mappers.HubUserMapper;
import chatbot.api.skillHub.domain.HubUserInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class HubDeleter {

    @Autowired
    private HubMapper hubMapper;

    @Autowired
    private HubUserMapper hubUserMapper;



    @Transactional
    public ResponseDto explicitDeleter(HubUserInfoDto role) {

        ResponseDto responseDto = new ResponseDto().builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();


        try {
            // skillHubUserMapper.deleterHubUser
            responseDto.setMsg("delete fail : hub_user 테이블의 레코드를 삭제하는데 실패");
            hubUserMapper.deleteHubUser(role);

            // skillHubMapper.deleterUser
            responseDto.setMsg("delete fail : hub 테이블의 레코드를 삭제하는데 실패");
            hubMapper.deleteHub(role.getHubSeq());

            responseDto.setMsg("delete success : hub and role");
            responseDto.setStatus(HttpStatus.OK);

        } catch (Exception e) {
            log.debug("deleter를 발생하는 와중에 exception 발생");
            e.printStackTrace();

        } finally {
            return responseDto;
        }
    }
}
