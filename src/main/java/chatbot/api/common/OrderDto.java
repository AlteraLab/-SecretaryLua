package chatbot.api.common;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderDto {
	private String msg;
	private HttpStatus status;
	private Object dataset;
	private LocalDateTime orderDateTime;
}