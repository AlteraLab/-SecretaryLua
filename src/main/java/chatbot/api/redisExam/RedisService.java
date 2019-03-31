package chatbot.api.redisExam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.print.DocFlavor;

@Service
public class RedisService {

/*
    @Autowired
    RedisTemplate<String, Object> template;
*/

/*
    public void exam() {
        // value operation
        ValueOperations<String, Object> values = template.opsForValue();

        // set
        values.set("123125324413", new Student("1", "lee", Student.Gender.FEMALE, 70));

        // get
        System.out.println(values.get("123125324413").toString());
    }*/


//-------------------------------------------------------------------------

    @Autowired
    private StudentRepository studentRepository;



    public void save() {
        Student student = new Student("1", "lee", Student.Gender.FEMALE, 70);
        studentRepository.save(student);
    }

    public void get() {
        Student reStudent = studentRepository.findById("1").get();
        System.out.println(reStudent.getName());
    }

    public void delete() {
        studentRepository.deleteById("1");
    }

    // 다시 살펴볼 필요가 있음
    public void update() {
        Student reStudent = studentRepository.findById("1").get();
        reStudent.setName("Hello World");
        studentRepository.save(reStudent);
    }






}
