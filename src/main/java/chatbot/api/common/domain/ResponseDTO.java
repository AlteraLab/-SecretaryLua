package chatbot.api.common.domain;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseDTO {
	private String msg;
	private String title;
	private HttpStatus status;
	private Object data;
}
