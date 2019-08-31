package chatbot.api.common.domain.kakao.openbuilder.responseVer2.component.basicCard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Link {

    private String mobile;

    private String ios;

    private String android;

    private String pc;

    private String mac;

    private String win;

    private String web;
}
/*
필드명	  타입	  필수 여부	  설명

mobile	string	X	ios와 android를 아우르는 mobile link입니다.
ios	string	X	ios의 웹이나 앱을 실행하는 link입니다.
android	string	X	android의 웹이나 앱을 실행하는 link입니다.
pc	string	X	mac과 window를 아우르는 pc link입니다.
mac	string	X	mac의 웹이나 앱을 실행하는 link입니다.
win	string	X	window의 웹이나 앱을 실행하는 link입니다.
web	string	X	모든 기기를 아우르는 link입니다.

링크 우선순위

링크는 다음과 같은 우선순위를 갖습니다.

모바일: web < mobile < ios or android (os에 따라 적용)
pc: web < pc < mac or win (os에 따라 적용))
예를 들면, ios 기기에 대하여, Link의 값이 mobileURL, iosURL를 갖으면 위의 규칙에 의하여 iosURL가 사용자에게 노출됩니다.

안드로이드 기기에 대하여, Link의 값이 mobileURL, iosURL를 갖으면 iosURL이 우선 순위를 갖지 못하기 때문에 mobileURL이 사용자에게 노출됩니다.
 */