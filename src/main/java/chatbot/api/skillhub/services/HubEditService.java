package chatbot.api.skillhub.services;

import chatbot.api.common.domain.ResponseDTO;
import chatbot.api.mappers.HubMapper;
import chatbot.api.skillhub.domain.HubInfoDTO;
import chatbot.api.skillhub.domain.HubVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import static chatbot.api.skillhub.utils.HubConstants.EXCEPTION_MSG_DURING_EDITER;
import static chatbot.api.skillhub.utils.HubConstants.FAIL_MSG_BECAUSE_NO_EXIST;
import static chatbot.api.skillhub.utils.HubConstants.SUCCESS_MSG_EDIT_HUB;

@Service
@Slf4j
@AllArgsConstructor
public class HubEditService {

    private HubMapper hubMapper;


    // 허브가 연결된 장소가 변경되었을 때, DB에 허브의 공용 IP 주소를 수정하는 기능
    public ResponseDTO editerHubUpnpIp(HubVO hubInfoVo) {
        System.out.println("\n");
        log.info("Info -> Update Upnp Ip Mehtod -> Editer Hub Upnp Ip");

        HubInfoDTO hub = hubMapper.getHubInfoByMacAddr(hubInfoVo.getMacAddr());
        if(hub == null) {
            log.info("Hub == NULL");
            return ResponseDTO.builder()
                    .msg(FAIL_MSG_BECAUSE_NO_EXIST)
                    .status(HttpStatus.OK)
                    .build();
        }

        hubMapper.editHubIp(hubInfoVo.getMacAddr(),
                hubInfoVo.getExternalIp(),
                hubInfoVo.getExternalPort());

        return ResponseDTO.builder()
                .msg(SUCCESS_MSG_EDIT_HUB)
                .status(HttpStatus.OK)
                .build();
    }



    public ResponseDTO editer(String macAddr,
                              String hubName,
                              String searchId,
                              String desc,
                              String externalIp,
                              int externalPort,
                              String beforeIp) {


        ResponseDTO responseDto = ResponseDTO.builder()
                .data(null)
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();

        try {
            hubMapper.editHub(macAddr,
                            hubName,
                            searchId,
                            desc,
                            externalIp,
                            externalPort,
                            beforeIp);

            responseDto.setStatus(HttpStatus.OK);
            responseDto.setMsg(SUCCESS_MSG_EDIT_HUB);

        } catch (Exception e) {
            responseDto.setMsg(EXCEPTION_MSG_DURING_EDITER);
            e.printStackTrace();

        } finally {
            return responseDto;
        }
    }
}
