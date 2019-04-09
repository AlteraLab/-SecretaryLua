package chatbot.api.order.Repository;

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
        this.hashOperations.put(this.KEY, mainOrder.getProviderId(), mainOrder);
    }

    @Override
    public MainOrder find(String providerId) {
        return (MainOrder) this.hashOperations.get(this.KEY, providerId);
    }
}
/*
    HashOperations
    HashOperations performs Redis map specific operations working on a hash. Find some of its methods.
    1. putIfAbsent(H key, HK hashKey, HV value): Sets the value of a hash hashKey only if hashKey does not exist.
    2. put(H key, HK hashKey, HV value): Sets the value of a hash hashKey.
    3. get(H key, Object hashKey): Fetches value for given hashKey from hash at key.
    4. size(H key): Fetches size of hash at key.
    5. entries(H key): Fetches entire hash stored at key.
    6. delete(H key, Object... hashKeys): Deletes given hash hashKeys at key
*/
