package chatbot.api.user.domain;

import lombok.*;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserInfoDto {

    private Long userId;

    private String providerId;

    private String name;

    private String email;

    private String profileImage;

/*    private Timestamp createdAt;

    private Timestamp updatedAt;*/
}
