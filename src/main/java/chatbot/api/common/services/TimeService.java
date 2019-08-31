package chatbot.api.common.services;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import ch.qos.logback.classic.pattern.RootCauseFirstThrowableProxyConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Service
public class TimeService {


    public Timestamp convertTimeStampFromObject(Object params) {
        log.info("================== convert TimeStamp From Object 시작 ==================");
        log.info("INFO >> Params -> " + params.toString());
        String strTime = null;

        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(params.toString());
            strTime = jsonObject.get("value").toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("Info >> setDateTime -> " + strTime);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        Date date = null;
        try {
            date = simpleDateFormat.parse(strTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Long lDateTime = date.getTime();

        log.info("================== convert TimeStamp From Object 종료 ==================");
        return new Timestamp(lDateTime);
    }
}
