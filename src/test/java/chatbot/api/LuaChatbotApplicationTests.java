package chatbot.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LuaChatbotApplicationTests {

	@Autowired
	private TestRestTemplate testRestTemplate;

	//현재 active환경은?
	@Test
	public void contextLoads() {
		/*
		//when
		String profile = testRestTemplate.getForObject("/profile", String.class);

		//then
		assertThat(profile).isEqualTo("develop");
		*/
	}

}