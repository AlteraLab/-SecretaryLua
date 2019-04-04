package chatbot.api.common.domain.kakao.openbuilder.responseVer2;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDtoVerTwo {

    private String version;

    private SkillTemplate template;

    private Object context;

    private Object data;
}
