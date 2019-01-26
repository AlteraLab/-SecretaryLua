package chatbot.api;

import chatbot.api.common.config.AppProperties;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
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
