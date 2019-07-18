package chatbot.api.common.config;

import chatbot.api.mappers.HubMapper;
import chatbot.api.textbox.domain.Build;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@Slf4j
@EnableRedisRepositories
@PropertySource("classpath:application.yml")
public class RedisConfig extends CachingConfigurerSupport {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Autowired
    private HubMapper hubMapper;



    // RedisConnectionFactory를 통해 내장 혹은 외부의 Redis를 연결
    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory();
        lettuceConnectionFactory.setHostName(this.redisHost);
        lettuceConnectionFactory.setPort(this.redisPort);
        return lettuceConnectionFactory;
    }

    // Redis Command 를 도와주는 Template, 이는 Redis 데이터에 쉽게 접근하기 위한 코드를 제공합니다.
    // RedisTemplate을 통해 RedisConnection에서 넘겨준 byte 값을 객체 직렬화합니다.
    @Bean
    public RedisTemplate<String, Build> redisTemplateAboutBuild(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Build.class));
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        return redisTemplate;
    }

    @Bean
    public RedisTemplate<String, Boolean> redisTemplateAboutKeepAlive(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Boolean.class));
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        return redisTemplate;
    }

    // 데이터 만료시 발생
    @Bean
    RedisMessageListenerContainer keyExpirationListenerContainer(LettuceConnectionFactory lettuceConnectionFactory) {

        RedisMessageListenerContainer listenerContainer = new RedisMessageListenerContainer();

        listenerContainer.setConnectionFactory(lettuceConnectionFactory);
        listenerContainer.addMessageListener((message, pattern) -> {
            log.info("Message -> " + message);
            if(message.toString().contains(":")) {  // message 에  ":" 이 포함되어 있다면,
                // message (허브 맥 주소) 를 이용해서 데이터베이스에서 해당 허브의 status 값을 false 로 바꾼다.
                log.info("Message 에 \":\" 데이터가 포함되어 있습니다.");
                hubMapper.editStateToFalseWhenKeyExpired(message.toString());
            }
        }, new PatternTopic("__keyevent@*__:expired"));

        return listenerContainer;
    }
}