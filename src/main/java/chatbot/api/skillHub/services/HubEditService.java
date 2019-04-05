package chatbot.api.skillHub.services;

import chatbot.api.common.domain.ResponseDto;
import chatbot.api.mappers.HubMapper;
import chatbot.api.skillHub.domain.HubInfoVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static chatbot.api.skillHub.utils.HubConstants.EXCEPTION_MSG_DURING_EDITER;
import static chatbot.api.skillHub.utils.HubConstants.SUCCESS_MSG_EDIT_HUB;

@Service
@Slf4j
@AllArgsConstructor
public class HubEditService {

    private HubMapper hubMapper;



    public ResponseDto editer(HubInfoVo hubInfoVo) {

        ResponseDto responseDto = ResponseDto.builder()
                .data(null)
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();

        try {
            hubMapper.editHubAboutIpAndPort(hubInfoVo.getHubSequence(),
                                            hubInfoVo.getExternalIp(),
                                            hubInfoVo.getInternalIp(),
                                            hubInfoVo.getExternalPort(),
                                            hubInfoVo.getInternalPort());

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
