package chatbot.api.textbox.repository;

import chatbot.api.textbox.domain.Build;
import chatbot.api.textbox.domain.reservation.ReservationListDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Slf4j
@Repository
public class ReservationListRepository {

    private RedisTemplate<String, ReservationListDTO> redisTemplate;

    private ValueOperations<String, ReservationListDTO> valueOperations;

    @Autowired
    public BuildRepository buildRepository;

    public ReservationListRepository(RedisTemplate<String, ReservationListDTO> redisTemplate) {
        log.info("INFO -> Construct : ReservationListRepository Start");
        this.redisTemplate = redisTemplate;
        this.valueOperations = this.redisTemplate.opsForValue();
    }

    public void save(String providerId, ReservationListDTO reservationList) {
        Build reBuild = buildRepository.find(providerId);
        reservationList.setUrl("http://" +
                reBuild.getPath().getExternalIp() + ":" +
                reBuild.getPath().getExternalPort() + "/hub/reservation/");  // 마지막에 resId 만 추가해주면됨
        buildRepository.delete(providerId);
        log.info("INFO -> ReservationListRepository.save(" + reservationList.toString() + ")");
        this.valueOperations.set(providerId, reservationList, 300L, TimeUnit.SECONDS);
    }

    public ReservationListDTO find(String providerId) {
        ReservationListDTO reservationList = this.valueOperations.get(providerId);
        if(reservationList == null) {
            log.info("INFO -> ReservationListRepository.get(" + providerId + ")" + " == NULL");
        } else {
            log.info("INFO -> ReservationListRepository.get(" + providerId + ")" + " == " + reservationList);
        }
        return reservationList;
    }

    public void delete(String providerId) {
        log.info("INFO -> ReservationList.delete(" + providerId + ")");
        this.valueOperations.set(providerId, null, 1L, TimeUnit.NANOSECONDS);
    }
}
