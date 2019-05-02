package chatbot.api.user.domain;

import lombok.Data;

@Data
public class UserRegisterVo {

    // 삭제
    private Long userId;        // 삭제할 유저의 아이디

    // 등록
    private Long hubId;         // 유저를 등록할 허브 아이디
    private String email;       // 등록할 유저의 이메일
}
