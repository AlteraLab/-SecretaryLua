package chatbot.api.user.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequestDto {
    // hub 쪽에서 스킬 서버 페이지로 redirect 받을 데이터들
    private String beforeIp;
    private String currentIp;
    private String port;
}
