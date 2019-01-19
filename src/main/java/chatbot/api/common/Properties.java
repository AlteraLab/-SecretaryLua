package chatbot.api.common;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Properties {
    private String plusfriendUserKey;
    private String appUserId;
}
/*
"properties": {
  "plusfriendUserKey": "<플러스 친구 사용자 id>",
  "appUserId": "<app user id>"
}
 */