package chatbot.api.common;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestDto {
	private Intent intent;
	private UserRequest userRequest;
	private ChatBot bot;
	private Action action;  // 구현해줘야 할듯

	// 지울 수도 있음
//	private Contexts contexts;

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
	  "contexts": [],                                     /////
	  "bot": {
	    "id": "5c1373ff05aaa7796a7c85fa",
	    "name": "봇 이름"
	  "action": {
	  },
	    "name": "d543apevuo",
	    "clientExtra": null,
	    "params": {},
	    "id": "speebdqpbe5so77fvijctga6",
	    "detailParams": {}
	  }
	}


*/