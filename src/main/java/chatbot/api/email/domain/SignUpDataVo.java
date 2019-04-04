package chatbot.api.email.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDataVo {

    // react앱에서 보낼 데이터들

    private String id;

    private String email;

    private String password;
}
