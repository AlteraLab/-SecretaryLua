package chatbot.api.hublog.services;

import chatbot.api.common.domain.ResponseDTO;
import chatbot.api.hublog.domain.HubLogVO;
import chatbot.api.mappers.HubLogMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

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

        // 로그 조회
        List<HubLogVO> hubLogs;
        try {
            hubLogs = hubLogMapper.getHubLog(hubId);
        } catch (Exception e) {
            log.info(EXCEPTION_MSG_FAILED_GET_HUB_LOG);
            responseDto.setMsg(EXCEPTION_MSG_FAILED_GET_HUB_LOG);
            e.printStackTrace();
            return responseDto;
        }

        // 하드웨어별로 로그 분류
        HashMap<Integer, List<HubLogVO>> hubLogMap = new HashMap<Integer, List<HubLogVO>>();
        List<HubLogVO> logs = null;
        for(HubLogVO hubLog : hubLogs) {
            if(hubLogMap.containsKey(hubLog.getHrdwrId())) logs = hubLogMap.get(hubLog.getHrdwrId());
            else                                           logs = new ArrayList<HubLogVO>();
            logs.add(hubLog);
            hubLogMap.put(hubLog.getHrdwrId(), logs);
        }

        // 정렬
        Iterator<Integer> keys = hubLogMap.keySet().iterator();
        List<HubLogVO> sortedLogs = null;
        while(keys.hasNext()) {
            sortedLogs = hubLogMap.get(keys.next());
            Collections.sort(sortedLogs, new Comparator<HubLogVO>() {
                @Override
                public int compare(HubLogVO o1, HubLogVO o2) {
                    return o1.getRecordedAt().compareTo(o2.getRecordedAt());
                }
            });
            Collections.reverse(sortedLogs);
        }

        responseDto.setStatus(HttpStatus.OK);
        responseDto.setMsg(SUCCESS_MSG_GET_FROM_HUB_LOG);
        responseDto.setData(new Object(){
            public HashMap<Integer, List<HubLogVO>> logs = hubLogMap;
        });
        return responseDto;
    }
}
