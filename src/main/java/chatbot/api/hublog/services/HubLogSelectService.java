package chatbot.api.hublog.services;

import chatbot.api.common.domain.ResponseDTO;
import chatbot.api.hublog.domain.HubLogVO;
import chatbot.api.mappers.HubLogMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static chatbot.api.hublog.utils.HubLogConstatns.EXCEPTION_MSG_FAILED_GET_HUB_LOG;
import static chatbot.api.hublog.utils.HubLogConstatns.SUCCESS_MSG_GET_FROM_HUB_LOG;

@Service
@Slf4j
@AllArgsConstructor
public class HubLogSelectService {

    private HubLogMapper hubLogMapper;



    public ResponseDTO getHubLogByHubId(Long hubId) {

        log.info("HubLogSelectService.getHubLogByHubId");
        ResponseDTO responseDto = new ResponseDTO().builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();

        List<HubLogVO> hubLogs;
        try {
            hubLogs = hubLogMapper.getHubLog(hubId);
        } catch (Exception e) {
            log.info(EXCEPTION_MSG_FAILED_GET_HUB_LOG);
            responseDto.setMsg(EXCEPTION_MSG_FAILED_GET_HUB_LOG);
            e.printStackTrace();
            return responseDto;
        }

        // 정렬
        Collections.sort(hubLogs, new Comparator<HubLogVO>() {
            @Override
            public int compare(HubLogVO o1, HubLogVO o2) {
                return o1.getRecordedAt().compareTo(o2.getRecordedAt());
            }
        });

        // 앞에서 부터 데이터 100개 자르기
        if(hubLogs.size() > 100) {
            hubLogs = hubLogs.subList(0, 100);
        }

        // 역순 정렬
        Collections.reverse(hubLogs);

        List<HubLogVO> returnLogs = hubLogs;
        responseDto.setStatus(HttpStatus.OK);
        responseDto.setMsg(SUCCESS_MSG_GET_FROM_HUB_LOG);
        responseDto.setData(new Object(){
            public List<HubLogVO> logs = returnLogs;
        });
        return responseDto;
    }
}
