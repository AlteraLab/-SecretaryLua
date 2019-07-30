package chatbot.api.skillhub.services;

import chatbot.api.common.domain.ResponseDTO;
import chatbot.api.mappers.HubMapper;
import chatbot.api.mappers.RoleMapper;
import chatbot.api.mappers.UserMapper;
import chatbot.api.skillhub.domain.HubInfoDTO;
import chatbot.api.role.domain.RoleDTO;
import chatbot.api.skillhub.domain.HubVO;
import chatbot.api.user.domain.UserInfoDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static chatbot.api.role.utils.RoleConstants.EXCEPTION_MSG_DURING_REGISTER;
import static chatbot.api.role.utils.RoleConstants.FAIL_MSG_REGIST_ROLE_INTO_ROLE_TABLE;
import static chatbot.api.skillhub.utils.HubConstants.*;

@Slf4j
@Service
@AllArgsConstructor
public class HubRegisterService {

    private HubMapper hubMapper;

    private RoleMapper roleMapper;

    private UserMapper userMapper;

    private HubEditService hubEditService;

    private RestTemplate restTemplate;



    @Transactional
    public ResponseDTO register(Long userId, HubVO hubVo) {

        HubInfoDTO hub = null;

        // reactApp으로 부터 받은 beforeIp가 존재한다면, Ip가 바꼈다는 의미니까 hubEdit 실시
        if(hubVo.getBeforeIp() != null) {

            hub = hubMapper.getHubInfoByMacAddr(hubVo.getMacAddr());

            // 허브 조회 결과 만약 허브가 없다면
            try {
                if(hub == null)  throw new NullPointerException();
            } catch (Exception e) {
                log.info("해당 맥주소를 가진 허브가 없습니다.");
                e.printStackTrace();
                return ResponseDTO.builder()
                        .status(HttpStatus.EXPECTATION_FAILED)
                        .build();
            }

            log.info("맥 주소 존재 O");
            return hubEditService.editer(hubVo.getMacAddr(),
                    hubVo.getName(),
                    hubVo.getSearchId(),
                    hubVo.getDesc(),
                    hubVo.getExternalIp(),
                    hubVo.getExternalPort(),
                    hubVo.getBeforeIp());
        }

        hub = HubInfoDTO.builder()
                .adminId(userId)
                .hubName(hubVo.getName())
                .hubDescript(hubVo.getDesc())
                .hubSearchId(hubVo.getSearchId())
                .macAddr(hubVo.getMacAddr())
                .externalIp(hubVo.getExternalIp())
                .externalPort(hubVo.getExternalPort())
                .lastUsedTime(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .state(true)
                .beforeIp(null)
                .build();

        // not yet set hubSeq
        RoleDTO role = RoleDTO.builder()
                .userId(userId)
                .role(ROLE_ADMIN)
                .build();

        // log
        log.info("Role -> " + role.toString());
        log.info("Hub -> " + hub.toString());


        ResponseDTO responseDto = new ResponseDTO();
        try {
            // 관리자로 등록하려는 사용자의 id는 users 테이블에 있는 데이터인가?
            UserInfoDto user = userMapper.getUserByUserId(role.getUserId());
            if(user == null) {
                responseDto.setMsg(FAIL_MSG_NO_EXIST_USER_FROM_USERS_TABLE);
                responseDto.setStatus(HttpStatus.NO_CONTENT);
                return responseDto;
            }

            // hub 저장
            responseDto.setMsg(FAIL_MSG_REGIST_HUB_INTO_HUB_TABLE);
            hubMapper.save(hub);

            // role 저장
            responseDto.setMsg(FAIL_MSG_REGIST_ROLE_INTO_ROLE_TABLE);
            role.setHubId(hub.getHubId());
            roleMapper.save(role);

            String url = "http://"+ hubVo.getExternalIp()+":" + hubVo.getExternalPort() + "/hub";
            log.info("URL -> " + url);

            String resultByHub = restTemplate.postForObject(url, null, String.class);

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(resultByHub);
            resultByHub = String.valueOf(jsonObject.get("status"));

            log.info("Result By Hub -> " + resultByHub);
            if(resultByHub != "true") {
                responseDto.setMsg(EXCEPTION_MSG_DURING_REGISTER);
                throw new Exception();
            }

            responseDto.setMsg(SUCCESS_MSG_REGIST_INTO_HUB_AND_ROLL);
            responseDto.setStatus(HttpStatus.OK);

        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 직접 롤백 시킴
            responseDto.setStatus(HttpStatus.EXPECTATION_FAILED);
            log.info(EXCEPTION_MSG_DURING_REGISTER);
            e.printStackTrace();
        }
        return responseDto;
    }
}
