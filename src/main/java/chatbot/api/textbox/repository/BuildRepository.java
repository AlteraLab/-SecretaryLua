package chatbot.api.textbox.repository;

import chatbot.api.textbox.domain.Build;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Slf4j
@Repository
public class BuildRepository  {

    private RedisTemplate<String, Build> redisTemplate;

    private ValueOperations<String, Build> valueOperations;


    public BuildRepository(RedisTemplate<String, Build> redisTemplate) {
        log.info("INFO -> Construct : BuildRepository Start");
        this.redisTemplate = redisTemplate;
        this.valueOperations = this.redisTemplate.opsForValue();
    }


    public void save(Build build) {
        log.info("INFO -> BuildRepository.save(" + build.toString() + ")");
        this.valueOperations.set(build.getHProviderId(), build, 300L, TimeUnit.SECONDS);
    }

    public void update(Build reBuild) {
        log.info("INFO -> BuildRepository.update(" + reBuild.toString() + ")");
        this.valueOperations.set(reBuild.getHProviderId(), reBuild, 300L, TimeUnit.SECONDS);
    }

    public Build find(String providerId) {
        Build build = this.valueOperations.get(providerId);
        if(build == null) {
            log.info("INFO -> BuildRepository.get(" + providerId + ")" + " == NULL");
        } else {
            log.info("INFO -> BuildRepository.get(" + providerId + ")" + " == " + build);
        }
        return build;
    }

    public void delete(String providerId) {
        log.info("INFO -> BuildRepository.delete(" + providerId + ")");
        this.valueOperations.set(providerId, null, 1L, TimeUnit.NANOSECONDS);
    }
}
