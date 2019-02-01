package chatbot.api.common.domain.kakao.openbuilder;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRequest {
	private String timezone;
	private Object params;
	private Block block;
	private String utterance; //발화내용
	private String lang;
	private KakaoUser user;
}
