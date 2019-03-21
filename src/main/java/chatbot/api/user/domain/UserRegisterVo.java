package chatbot.api.user.domain;

import lombok.Data;

@Data
public class UserRegisterVo {

    private Long hubId;         // 유저를 등록할 허브

    private String email;   // 등록할 유저이 이메일

    //private Long userId;        // 등록할 유저
}
