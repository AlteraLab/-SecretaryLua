package chatbot.api.common;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class KakaoUser {
	private String id;
	private String type;
	private Object properties;
}
/*
"user": {
	"id": "266742",
	"type": "accountId",
	"properties": {}
}
 */
