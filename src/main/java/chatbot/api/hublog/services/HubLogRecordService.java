package chatbot.api.hublog.services;

import chatbot.api.common.domain.ResponseDTO;
import chatbot.api.hublog.domain.HubLogDTO;
import chatbot.api.mappers.HubLogMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static chatbot.api.hublog.utils.HubLogConstatns.EXCEPTION_MSG_FAILED_RECORD_HUB_LOG;
import static chatbot.api.hublog.utils.HubLogConstatns.SUCCESS_MSG_RECORD_INTO_HUB_LOG;

@Service
@Slf4j
@AllArgsConstructor
public class HubLogRecordService {

    private HubLogMapper hubLogMapper;


    public ResponseDTO record(HubLogDTO hubLogDto) {

        log.info("HubLogRecordService.record");
        ResponseDTO responseDto = new ResponseDTO().builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();

        try {
            hubLogMapper.saveHubLog(hubLogDto);
        } catch (Exception e) {
            log.info(EXCEPTION_MSG_FAILED_RECORD_HUB_LOG);
            responseDto.setMsg(EXCEPTION_MSG_FAILED_RECORD_HUB_LOG);
            e.printStackTrace();
            return responseDto;
        }

        responseDto.setStatus(HttpStatus.OK);
        responseDto.setMsg(SUCCESS_MSG_RECORD_INTO_HUB_LOG);
        return responseDto;
    }
}
