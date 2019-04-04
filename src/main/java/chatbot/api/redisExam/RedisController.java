package chatbot.api.redisExam;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class RedisController {

    @Autowired
    private RedisService redisService;


    /*@GetMapping("/redis")
    public void redisTest() {
        //redisService.keesunExam();
        redisService.exam();
    }*/

    @GetMapping("/redis/save")
    public void redisSave() {
        redisService.save();
    }

    @GetMapping("/redis/get")
    public void redisGet() {
        redisService.get();
    }

    @GetMapping("/redis/delete")
    public void redisDelete() {
        redisService.delete();
    }

    @GetMapping("/redis/update")
    public void redisUpdate() {
        redisService.update();
    }

}
