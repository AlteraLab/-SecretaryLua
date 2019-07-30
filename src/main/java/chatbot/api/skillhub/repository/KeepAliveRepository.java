package chatbot.api.skillhub.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Slf4j
@Repository
public class KeepAliveRepository {

    private RedisTemplate<String, Boolean> redisTemplate;

    private ValueOperations<String, Boolean> valueOperations;

    public KeepAliveRepository(RedisTemplate<String, Boolean> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.valueOperations = this.redisTemplate.opsForValue();
    }

    // Redis 에 허브 맥 주소 추가
    public void save(String hubMacAddr, Boolean status) {
        log.info("Redis 내에 데이터가 없습니다. 데이터를 추가합니다");
        valueOperations.set(hubMacAddr, status, 20L, TimeUnit.SECONDS);
    }

    // Redis 에 허브 맥 주소 갱신
    public void update(String hubMacAddr, Boolean status) {
        log.info("데이터 갱신");
        valueOperations.set(hubMacAddr, status, 20L, TimeUnit.SECONDS);
    }

    // 허브 맥 주소 조회
    public Boolean find(String hubMacAddr) {
        return valueOperations.get(hubMacAddr);
    }
}
