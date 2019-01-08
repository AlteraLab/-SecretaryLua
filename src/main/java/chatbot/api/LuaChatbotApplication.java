package chatbot.api;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class LuaChatbotApplication {

	public static final String APP_LOCATIONS = "spring.config.location=" +
			"classpath:application.yml," +
			"/app/config/lua-skill-server/production-application.yml";

	public static void main(String[] args) {
		new SpringApplicationBuilder(LuaChatbotApplication.class)
				.properties(APP_LOCATIONS)
				.run(args);
	}
}
