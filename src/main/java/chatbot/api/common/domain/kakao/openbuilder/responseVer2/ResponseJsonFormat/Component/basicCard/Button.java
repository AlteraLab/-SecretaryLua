package chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseJsonFormat.Component.basicCard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Button {

    private String label;

    private String action;

    private String webLinkUrl;



    private Link osLink;

    private String phoneNumber;

    private String blockId;

    private String messageText;

    private Object extra;
}
/*
필드명	타입	필수 여부	설명
label	string	O	버튼에 적히는 문구입니다.
action	string	O	버튼 클릭시 수행될 작업입니다.
최대 8자의 길이를 갖습니다.
webLinkUrl	string	action: webLink	웹 브라우저를 열고 webLinkUrl 의 주소로 이동합니다.
osLink	Link	action: osLink	osLink 값에 따라서 웹의 주소로 이동하거나 앱을 실행합니다.
messageText	string	action: message or block
message: 사용자의 발화로 messageText를 내보냅니다. (바로가기 응답의 메세지 연결 기능과 동일)
block: 블록 연결시 사용자의 발화로 노출됩니다.
phoneNumber	string	action: phone	phoneNumber에 있는 번호로 전화를 겁니다.
blockId	string	action: block	blockId를 갖는 블록을 호출합니다. (바로가기 응답의 블록 연결 기능과 동일)
extra	Any		block이나 message action으로 블록 호출시, 스킬 서버에 추가적으로 제공하는 정보

action 종류
webLink: 웹 브라우저를 열고 webLinkUrl 의 주소로 이동합니다.
osLink: osLink 값에 따라서 웹의 주소로 이동하거나 앱을 실행합니다.
message: 사용자의 발화로 messageText를 실행합니다. (바로가기 응답의 메세지 연결 기능과 동일)
phone: phoneNumber에 있는 번호로 전화를 겁니다.
block: blockId를 갖는 블록을 호출합니다. (바로가기 응답의 블록 연결 기능과 동일)
messageText가 있다면, 해당 messageText가 사용자의 발화로 나가게 됩니다.
messageText가 없다면, button의 label이 사용자의 발화로 나가게 됩니다.
share: 말풍선을 다른 유저에게 공유합니다. share action은 특히 케로셀을 공유해야 하는 경우 유용합니다.
 */