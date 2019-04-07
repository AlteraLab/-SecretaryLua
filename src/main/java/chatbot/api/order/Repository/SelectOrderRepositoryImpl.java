package chatbot.api.order.Repository;

import chatbot.api.order.domain.SelectOrderVo;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SelectOrderRepositoryImpl implements SelectOrderRepository{

    private RedisTemplate<String, Object> redisTemplate;

    private HashOperations hashOperations;

    private static final String KEY = "KEY";


    public SelectOrderRepositoryImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }



    @Override
    public void save(SelectOrderVo selectOrderVo) {
        this.hashOperations.put(KEY, selectOrderVo.getProviderId(), selectOrderVo);
    }

    @Override
    public SelectOrderVo find(String providerId) {
        return (SelectOrderVo) this.hashOperations.get(KEY, providerId);
    }
}
