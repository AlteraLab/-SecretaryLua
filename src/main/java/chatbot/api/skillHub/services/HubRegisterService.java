package chatbot.api.skillHub.services;

import chatbot.api.common.domain.ResponseDto;
import chatbot.api.mappers.HubMapper;
import chatbot.api.mappers.RoleMapper;
import chatbot.api.mappers.UserMapper;
import chatbot.api.skillHub.domain.HubInfoDto;
import chatbot.api.role.domain.RoleDto;
import chatbot.api.skillHub.domain.HubVo;
import chatbot.api.user.domain.UserInfoDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static chatbot.api.role.utils.RoleConstants.EXCEPTION_MSG_DURING_REGISTER;
import static chatbot.api.role.utils.RoleConstants.FAIL_MSG_REGIST_ROLE_INTO_ROLE_TABLE;
import static chatbot.api.skillHub.utils.HubConstants.*;

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
    public ResponseDto register(Long userId, HubVo hubVo) {

        HubInfoDto hub = null;

        // reactApp으로 부터 받은 beforeIp가 존재한다면, Ip가 바꼈다는 의미니까 hubEdit 실시
        if(hubVo.getBeforeIp() != null) {

            hub = hubMapper.getHubInfoByMacAddr(hubVo.getMacAddr());

            // 허브 조회 결과 만약 허브가 없다면
            try {
                if(hub == null)  throw new Exception();
            } catch (Exception e) {
                log.info("해당 맥주소를 가진 허브가 없습니다.");
                e.printStackTrace();
                return ResponseDto.builder()
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

        hub = HubInfoDto.builder()
                .adminId(userId)
                .hubName(hubVo.getName())
                .hubDescript(hubVo.getDesc())
                .hubSearchId(hubVo.getSearchId())
                .macAddr(hubVo.getMacAddr())
                .externalIp(hubVo.getExternalIp())
                .externalPort(hubVo.getExternalPort())
                .beforeIp(hubVo.getBeforeIp())
                .lastUsedTime(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .state(true)
                .build();

        // not yet set hubSeq
        RoleDto role = RoleDto.builder()
                .userId(userId)
                .role(ROLE_ADMIN)
                .build();

        // log
        log.info(role.toString());
        log.info(hub.toString());


        ResponseDto responseDto = new ResponseDto().builder().build();

        try {
            // 관리자로 등록하려는 사용자의 id는 users 테이블에 있는 데이터인가?
            UserInfoDto user = userMapper.getUserByUserId(role.getUserId());
            if(user == null) {
                responseDto.setMsg(FAIL_MSG_NO_EXIST_USER_FROM_USERS_TABLE);
                responseDto.setStatus(HttpStatus.EXPECTATION_FAILED);
                return responseDto;
            }

            // hub 저장
            responseDto.setMsg(FAIL_MSG_REGIST_HUB_INTO_HUB_TABLE);
            hubMapper.save(hub);

            // role 저장
            responseDto.setMsg(FAIL_MSG_REGIST_ROLE_INTO_ROLE_TABLE);
            role.setHubId(hub.getHubId());
            roleMapper.save(role);

/*            String url = "http://203.250.32.29:" + hubVo.getExternalPort() + "/hub";
            log.info(url);

            String resultByHub = restTemplate.postForObject(url, null, String.class);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(resultByHub);
            resultByHub = String.valueOf(jsonObject.get("status"));

            log.info("Result By Hub :: " + resultByHub);
            if(resultByHub != "true") {
                responseDto.setMsg(EXCEPTION_MSG_DURING_REGISTER);
                throw new Exception();
            }*/

            responseDto.setMsg(SUCCESS_MSG_REGIST_INTO_HUB_AND_ROLL);
            responseDto.setStatus(HttpStatus.OK);

        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 직접 롤백 시킴
            responseDto.setStatus(HttpStatus.EXPECTATION_FAILED);
            log.info(EXCEPTION_MSG_DURING_REGISTER);
            e.printStackTrace();
        } finally {
            return responseDto;
        }
    }
}
