package chatbot.api.skillHub.services;

import chatbot.api.common.domain.ResponseDto;
import chatbot.api.mappers.HubMapper;
import chatbot.api.mappers.HubUserMapper;
import chatbot.api.mappers.UserMapper;
import chatbot.api.skillHub.domain.HubInfoDto;
import chatbot.api.skillHub.domain.HubUserInfoDto;
import chatbot.api.user.domain.UserInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class HubRegister {

    @Autowired
    private HubMapper hubMapper;

    @Autowired
    private HubUserMapper hubUserMapper;

    @Autowired
    private UserMapper userMapper;



    @Transactional
    public ResponseDto register(HubInfoDto hub, HubUserInfoDto role) {


        ResponseDto responseDto = new ResponseDto().builder()
                                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .build();

        try {
            // 관리자로 등록하려는 사용자의 id는 users 테이블에 있는 데이터인가?
            UserInfoDto user = userMapper.getUserByUserId(role.getUserSeq());
            if(user == null) {
                responseDto.setMsg("fail : 유저 테이블에 유저가 없음");
                responseDto.setStatus(HttpStatus.ACCEPTED);

                return responseDto;
            }

            // hub 저장
            responseDto.setMsg("fail : 허브 정보를 저장하는데 실패 했습니다.");
            hubMapper.save(hub);

            // role 객체의 hubSeq 멤버를 set
            role.setHubSeq(hub.getHubSeq());

            // role 저장
            responseDto.setMsg("fail : role을 저장하는데 실패했습니다.");
            hubUserMapper.save(role);

            responseDto.setMsg("success : 등록");
            responseDto.setStatus(HttpStatus.CREATED);
            responseDto.setData(role);

        } catch (Exception e) {
            log.info("hub, roll을 등록하는 과정에서 exception이 발생했습니다.");
            e.printStackTrace();

        } finally {
            return responseDto;
        }

    }
}
