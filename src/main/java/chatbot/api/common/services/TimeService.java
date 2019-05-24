package chatbot.api.common.services;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import ch.qos.logback.classic.pattern.RootCauseFirstThrowableProxyConverter;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class TimeService {

    public int convertFromDateTimeToMinute(Object params) {

        log.info("================== convertFromDateTimeToMinute 시작 ==================");

        log.info("INFO >> Params -> " + params.toString());
        String setDateTime = null;

        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(params.toString());
            setDateTime = jsonObject.get("value").toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("Info >> setDateTime -> " + setDateTime);

        String[] splitDateTime = setDateTime.split("T");

        String date = splitDateTime[0];
        String time = splitDateTime[1];

        date = date.replaceAll("-", "");
        time = time.replaceAll(":", "");


        // 시간 비교 코드
        String reqDateStr =  date + time;
        reqDateStr = reqDateStr.substring(0, reqDateStr.length() - 2);
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMMddHHmm");
        Date curDate = new Date();
        Date reqDate = null;

        try {
            // 요청 시간을 date로 parsing 후 time 가져오기
            reqDate = dateFormat.parse(reqDateStr);
            // 현재 시간을 요청 시간의 형태로 format후 time 가져오기
            curDate = dateFormat.parse(dateFormat.format(curDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
        long reqDateTime = reqDate.getTime();
        long curDateTime = curDate.getTime();

        // 분으로 표현
        long minute = (reqDateTime - curDateTime) / 60000;

        log.info("INFO >> 요청 시간 -> " + reqDate);
        log.info("INFO >> 현재 시간 -> " + curDate);
        log.info("INFO >>   분 차이 -> " + minute);

        if ((minute <= 300) && (minute >= 0)) {
            log.info("INFO >> 정상적인 시간 설정 -> " + minute);
        } else {
            log.info("INFO >> 비 정상적인 시간 설정 -> " + minute);
            minute = -1;   // 비 정상적인 시간 설정시 minute 을 -1 로 초기화
        }

        log.info("================== convertFromDateTimeToMinute 끝   ==================");
        return (int) minute;
    }
}
