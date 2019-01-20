package chatbot.api.common.domain.kakao;

import chatbot.api.common.domain.kakao.Block;
import chatbot.api.common.domain.kakao.KakaoUser;
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
/*
"userRequest": {
	"timezone": "Asia/Seoul",

	"params": {
		"ignoreMe": "true"
	},

	"block": {
		"id": "j0zya4e7fi648hkvunoqlsep",
		"name": "블록 이름"
	},

	"utterance": "발화 내용",

	"lang": null,

	"user": {
		"id": "266742",
		"type": "accountId",
		"properties": {}
	}
}
*/