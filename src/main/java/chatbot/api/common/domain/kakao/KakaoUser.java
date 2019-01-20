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
/*
"user": {
	"id": "266742",
	"type": "accountId",
	"properties": {
        "plusfriendUserKey": "<플러스 친구 사용자 id>",
        "appUserId": "<app user id>"
    }
}
 */
