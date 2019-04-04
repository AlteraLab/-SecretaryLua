package chatbot.api.redisExam;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("Student")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Serializable {

    // redis value operation에 사용할 VO 객체인 Student 클래스를 생성

    public enum Gender {
        MALE,
        FEMALE
    }

    @Id
    private String id;

    private String name;

    private Gender gender;

    private int grade;
}
