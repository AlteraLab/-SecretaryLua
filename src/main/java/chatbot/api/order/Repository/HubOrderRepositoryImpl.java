package chatbot.api.order.Repository;

import chatbot.api.order.domain.HubOrderDto;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Map;

@Repository
public class HubOrderRepositoryImpl implements HubOrderRepository {

    private RedisTemplate<String, Object> redisTemplate;

    private HashOperations hashOperations;

    public HubOrderRepositoryImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }



    @Override
    public void save(String providerId, HubOrderDto hubOrderDto) {
        hashOperations.put(providerId, hubOrderDto.getHubId(), hubOrderDto);
    }

    @Override
    public ArrayList<HubOrderDto> findAll(String providerId) {
        Map<Long, HubOrderDto> map = this.hashOperations.entries(providerId);
        ArrayList<HubOrderDto> list = new ArrayList<>();
        for(Long hubId : map.keySet()) {
            list.add(map.get(hubId));
        }
        return list;
    }

    @Override
    public void deleteAllByProviderId(String providerId, Long hubId) {
        this.hashOperations.delete(providerId, hubId);
    }
}
