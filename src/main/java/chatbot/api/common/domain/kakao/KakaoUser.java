package chatbot.api.common.domain.kakao;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class KakaoUser {
	private String id;
	private String type;
	private Properties properties;
}
