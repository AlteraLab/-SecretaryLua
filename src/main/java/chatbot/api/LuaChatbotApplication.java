package chatbot.api;

import chatbot.api.common.security.oauth2.jwt.AppProperties;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@SpringBootApplication
@MapperScan(basePackages="chatbot.api.mappers")
@EnableConfigurationProperties(AppProperties.class)
@EnableScheduling
public class LuaChatbotApplication {

	public static final String APP_LOCATIONS = "spring.config.location=" +
			"classpath:application.yml," +
			"/app/config/lua-skill-server/production-application.yml";

	public static void main(String[] args) {

		/*log.trace("Hello World");
		log.debug("Hello World");
		log.info("Hello World");
		log.warn("Hello World");
		log.error("Hello World");*/

		new SpringApplicationBuilder(LuaChatbotApplication.class)
				.properties(APP_LOCATIONS)
				.run(args);
	}
}
