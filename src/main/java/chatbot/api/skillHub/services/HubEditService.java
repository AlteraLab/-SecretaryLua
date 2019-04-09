package chatbot.api.skillHub.services;

import chatbot.api.common.domain.ResponseDto;
import chatbot.api.mappers.HubMapper;
import chatbot.api.skillHub.domain.HubInfoDto;
import chatbot.api.skillHub.domain.HubVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import static chatbot.api.skillHub.utils.HubConstants.EXCEPTION_MSG_DURING_EDITER;
import static chatbot.api.skillHub.utils.HubConstants.FAIL_MSG_BECAUSE_NO_EXIST;
import static chatbot.api.skillHub.utils.HubConstants.SUCCESS_MSG_EDIT_HUB;

@Service
@Slf4j
@AllArgsConstructor
public class HubEditService {

    private HubMapper hubMapper;



    public ResponseDto editer(String macAddr,
                              String hubName,
                              String searchId,
                              String desc,
                              String externalIp,
                              int externalPort,
                              String beforeIp) {


        ResponseDto responseDto = ResponseDto.builder()
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



    public ResponseDto editerHubUPnPIp(HubVo hubInfoVo, HubEditService hubEditService) {

        HubInfoDto hub = hubMapper.getHubInfoByMacAddr(hubInfoVo.getMacAddr());
        if(hub.equals(null)) return ResponseDto.builder()
                .msg(FAIL_MSG_BECAUSE_NO_EXIST)
                .status(HttpStatus.OK)
                .build();

/*
        ResponseDto responseDto = hubEditService.editer(hub.getHubId(),
                                                        hubInfoVo.getExternalIp(),
                                                        hubInfoVo.getInternalIp(),
                                                        hubInfoVo.getExternalPort(),
                                                        hubInfoVo.getInternalPort());

        if(responseDto.getMsg().equals(SUCCESS_MSG_EDIT_HUB)) responseDto.setMsg("UPNP IP");

        return responseDto;
*/
        return ResponseDto.builder().build();
    }
}
