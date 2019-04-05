package chatbot.api.skillHub.services;

import chatbot.api.mappers.HubMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static chatbot.api.skillHub.utils.HubConstants.EXCEPTION_MSG_DURING_IMPLICIT_DELETE_HUB;


@Component
@Slf4j
@AllArgsConstructor
public class HubImplicitDeleteService {

    HubMapper hubMapper;


    @Scheduled(cron = "0 0 2 * * *")
    public void implicitDeleteHub() {

        Date expireDate = Date.from(LocalDateTime.now().minusDays(30).atZone(ZoneId.systemDefault()).toInstant());
        log.info(expireDate.toString());

        try {
            hubMapper.implicitDeleteHub(expireDate);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(EXCEPTION_MSG_DURING_IMPLICIT_DELETE_HUB);
        }
    }

/*
    @Scheduled(cron = "0 0 * * * *")
    public void implicitDeleteHub() {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        Calendar calendar = Calendar.getInstance();
        calendar.add(calendar.DAY_OF_MONTH, -30);

        String thirtyDaysAgo = formatter.format(calendar.getTime());
        Timestamp expireDate = Timestamp.valueOf(thirtyDaysAgo);
        log.info(expireDate.toString());

        try {
            hubMapper.implicitDeleteHub(expireDate);
        } catch (Exception e) {
            log.info(EXCEPTION_MSG_DURING_IMPLICIT_DELETE_HUB);
            e.printStackTrace();
        }
    }
     */
}
