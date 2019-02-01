package chatbot.api.common.domain.kakao.openbuilder;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestDto {
	private Intent intent;
	private UserRequest userRequest;
	private ChatBot bot;
	private Action action;
}

/*


{
	  "intent": {
	    "id": "2ogu4breptvy31iky34shchg",
	    "name": "블록 이름"
	  },
	  "userRequest": {
	    "timezone": "Asia/Seoul",
	    "params": {
	      "ignoreMe": "true"
	    },
	    "block": {
	      "id": "2ogu4breptvy31iky34shchg",
	      "name": "블록 이름"
	    },
	    "utterance": "발화 내용",
	    "lang": null,
	    "user": {
	      "id": "667652",
	      "type": "accountId",
	      "properties": {
	      	"plusfriendUserKey": "<플러스 친구 사용자 id>",
	      	"appUserId": "<app user id>"
	      }
	    }
	  },
	  "bot": {
	    "id": "5c1373ff05aaa7796a7c85fa",
	    "name": "봇 이름"
	  },
	  "action": {
	    "name": "d543apevuo",
	    "clientExtra": null,
	    "params": {},
	    "id": "speebdqpbe5so77fvijctga6",
	    "detailParams": {}
	  }
	}


*/