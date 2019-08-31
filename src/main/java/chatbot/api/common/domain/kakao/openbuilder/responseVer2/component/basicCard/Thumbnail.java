package chatbot.api.common.domain.kakao.openbuilder.responseVer2.component.basicCard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Thumbnail {

    private String imageUrl;

    private Link link;

    private int width;

    private int height;
}
