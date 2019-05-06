package chatbot.api.order.repository;

import chatbot.api.order.domain.MainOrder;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MainOrderRepositoryImpl implements MainOrderRepository {

    private static final String KEY = "MainOrder";

    private RedisTemplate<String, MainOrder> redisTemplate;

    private HashOperations hashOperations;


    // 생성자 주입
    public MainOrderRepositoryImpl(RedisTemplate<String, MainOrder> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }


    @Override
    public void save(MainOrder mainOrder) {
        this.hashOperations.put(this.KEY, mainOrder.getHProviderId(), mainOrder);
    }


    @Override
    public void update(MainOrder reMainOrder) {
        this.hashOperations.put(this.KEY, reMainOrder.getHProviderId(), reMainOrder);
    }


    @Override
    public void delete(String providerId) {
        this.hashOperations.delete(this.KEY, providerId);
    }


    @Override
    public MainOrder find(String providerId) {
        return (MainOrder) this.hashOperations.get(this.KEY, providerId);
    }
}
