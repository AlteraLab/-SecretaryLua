package chatbot.api.skillHub.services;

import chatbot.api.mappers.HubMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;


@Component
@Slf4j
@AllArgsConstructor
public class ImplicitDeleteHub {

    HubMapper hubMapper;


    @Scheduled(cron = "0 0 2 * * *")
    public void implicitDeleteHub() {

        Calendar calendar = Calendar.getInstance();
        calendar.add(calendar.DAY_OF_MONTH, -30);

        Date expireDate = calendar.getTime();
        log.info(expireDate.toString());

        try {
            hubMapper.implicitDeleteHub(expireDate);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("exception");
        }
    }
}
