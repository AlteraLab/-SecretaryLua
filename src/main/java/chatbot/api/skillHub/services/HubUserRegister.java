package chatbot.api.skillHub.services;

import chatbot.api.common.domain.ResponseDto;
import chatbot.api.mappers.HubMapper;
import chatbot.api.mappers.HubUserMapper;
import chatbot.api.mappers.UserMapper;
import chatbot.api.skillHub.domain.HubInfoDto;
import chatbot.api.skillHub.domain.HubUserInfoDto;
import chatbot.api.user.domain.UserInfoDto;
import chatbot.api.user.domain.UserRegisterVo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HubUserRegister {

    @Autowired
    UserMapper userMapper;

    @Autowired
    HubMapper hubMapper;

    @Autowired
    HubUserMapper hubUserMapper;



    // userRegisterVo(hubId, email), userprincipal.getId()
    public ResponseDto register(UserRegisterVo userRegisterVo, Long adminId) {


        ResponseDto responseDto = new ResponseDto().builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .data(null)
                .msg(null)
                .build();

        try {
            responseDto.setMsg("fail : 허브 검색 결과 없음");
            HubInfoDto hub = hubMapper.getHubInfo(userRegisterVo.getHubId());
            if(hub == null)                  return responseDto;

            responseDto.setMsg("fail : 허브 검색 결과는 있으나, 허브를 관리하는 유저가 아님, 즉, 다른 사용자를 추가할 수 없음");
            if(adminId != hub.getAdminSeq()) return responseDto;

            responseDto.setMsg("fail : 이미 해당 허브를 사용할 수 있는 사용자 입니다.");
            HubUserInfoDto hubUser = hubUserMapper.getHubUserInfo(userRegisterVo.getHubId(), userRegisterVo.getUserId());
            if(hubUser != null)  return responseDto;


            // finish valid check


            // init
            hubUser = new HubUserInfoDto().builder()
                    .hubSeq(userRegisterVo.getHubId())
                    .userSeq(userRegisterVo.getUserId())
                    .role("ROLE_USER")
                    .build();


            responseDto.setMsg("fail : 데이터를 저장하지 못했습니다.");
            hubUserMapper.save(hubUser);

            responseDto.setMsg("success : 허브 등록이 정상적으로 완료되었습니다.");
            responseDto.setStatus(HttpStatus.CREATED);
            responseDto.setData(hubUser);

        } catch (Exception e) {
            log.info("HunUserRegister 실행 중 exception이 발생");
            e.printStackTrace();

        }

        return responseDto;
    }
}