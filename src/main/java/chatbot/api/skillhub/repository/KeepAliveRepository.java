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
        valueOperations.set(hubMacAddr, status, 5L, TimeUnit.SECONDS);
    }

    // Redis 에 허브 맥 주소 갱신
    public void update(String hubMacAddr, Boolean status) {
        valueOperations.set(hubMacAddr, status, 5L, TimeUnit.SECONDS);
    }

    // 허브 맥 주소 조회
    public Boolean find(String hubMacAddr) {
        return valueOperations.get(hubMacAddr);
    }
}
