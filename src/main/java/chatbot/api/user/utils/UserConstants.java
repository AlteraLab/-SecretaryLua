package chatbot.api.user.utils;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserConstants {
    public static final String GUIDE_ENROLL = "등록하실려는 거군요!!! 아래의 입력 예시를 참고해서, 당신의 Ip와 비밀번호 4자리 입력해주세요!!" +
            "      만약, 당신의 Ip가 192.523.23.1 이고 사용하시려는 비밀번호가 0711 이라면 아래와 같이 입력하세요!  " +
            "      ex) 아이피 = 192.255.0.1, 비밀번호 = 0711 ";

    public static final String CREATE_USER_SUCCESS = "등록 성공";
    public static final String CREATE_USER_FAIL = "등록 실패";
}
