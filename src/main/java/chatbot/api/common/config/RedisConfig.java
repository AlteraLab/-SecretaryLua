package chatbot.api.common.config;

import chatbot.api.buildcode.domain.Build;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@EnableRedisRepositories
@Slf4j
@PropertySource("classpath:application.yml")
public class RedisConfig extends CachingConfigurerSupport {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;


    // RedisConnectionFactory를 통해 내장 혹은 외부의 Redis를 연결
    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        log.info("INFO -> Lettuce Connection Factory START");

        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory();
        lettuceConnectionFactory.setHostName(this.redisHost);
        lettuceConnectionFactory.setPort(this.redisPort);
        return lettuceConnectionFactory;
    }

    // Redis Command 를 도와주는 Template, 이는 Redis 데이터에 쉽게 접근하기 위한 코드를 제공합니다.
    // RedisTemplate을 통해 RedisConnection에서 넘겨준 byte 값을 객체 직렬화합니다.
    @Bean
    public RedisTemplate<String, Build> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        log.info("INFO -> Redis Template <String, Build>");

        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Build.class));
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        return redisTemplate;
    }
}

/*
    @Bean
    public RedisCacheManager cacheManager() {
        // https://stackoverflow.com/questions/50949773/cache-manager-with-spring-data-redis-2-0-3
        // https://github.com/supawer0728/simple-cache/tree/simple-cache

        //RedisCacheWriter cacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(this.jedisConnectionFactory());
        RedisCacheWriter cacheWriter = RedisCacheWriter.lockingRedisCacheWriter(this.jedisConnectionFactory());
        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(3L));

        return new RedisCacheManager(cacheWriter, cacheConfiguration);
    }


*/
/*    @Bean
    public RedisCacheManager cacheManager(JedisConnectionFactory jedisConnectionFactory) {
        log.info("============== RedisCacheManager START ==============");

        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(3))
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

        log.info("INFO >> TTL ->" + cacheConfiguration.getTtl().toString());

        RedisCacheManager redisCacheManager = RedisCacheManager.builder(jedisConnectionFactory)
                .cacheDefaults(cacheConfiguration)
                .build();

        log.info("============== RedisCacheManager END ==============");
        return redisCacheManager;
    }*/