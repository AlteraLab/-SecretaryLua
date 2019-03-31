package chatbot.api.redisExam;

import org.springframework.data.repository.CrudRepository;

// <객체, 키>
public interface StudentRepository extends CrudRepository<Student, String> {}
