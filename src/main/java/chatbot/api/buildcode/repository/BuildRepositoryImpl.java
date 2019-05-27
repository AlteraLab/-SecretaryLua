package chatbot.api.buildcode.repository;

import chatbot.api.buildcode.domain.Build;
import chatbot.api.common.config.RedisRepositoryConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@Data
public class BuildRepositoryImpl implements BuildRepository {

    private static final String KEY = "Build";

    private RedisTemplate<String, Build> redisTemplate;

    private HashOperations hashOperations;

    private RedisCacheManager redisCacheManager;

    private RedisRepositoryConfig redisRepositoryConfig;



    public BuildRepositoryImpl(RedisTemplate<String, Build> redisTemplate) {

        log.info("============= BuildRepositoryImpl =============");

        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();

        // https://stackoverflow.com/questions/51418161/how-to-create-rediscachemanager-in-spring-data-2-0-x
/*        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(5));
        this.redisCacheManager = RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(this.redisTemplate.getConnectionFactory())
                .cacheDefaults(redisCacheConfiguration)
                .build();
                */
    }

    @Override
    public void save(Build build) {
        this.hashOperations.put(this.KEY, build.getHProviderId(), build);
    }

    @Override
    public void update(Build reBuild) {
        this.hashOperations.put(this.KEY, reBuild.getHProviderId(), reBuild);
    }

    @Override
    public void delete(String providerId) {
        this.hashOperations.delete(this.KEY, providerId);
    }

    @Override
    public Build find(String providerId) {
        return (Build) this.hashOperations.get(this.KEY, providerId);
    }
}
