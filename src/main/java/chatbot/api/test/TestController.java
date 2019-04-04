package chatbot.api.test;

import chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseJsonFormat.Component.basicCard.Component;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseDtoVerTwo;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseJsonFormat.Component.basicCard.BasicCard;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseJsonFormat.Component.basicCard.Button;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.SkillTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class TestController {

    @PostMapping("/test")
    public ResponseDtoVerTwo testMethod() {

        ArrayList<Button> buttons = new ArrayList<Button>();
        buttons.add(Button.builder()
                .action("webLink")
                .label("test")
                .webLinkUrl("https://arabiannight.tistory.com/entry/324")
                .build());
        BasicCard basicCardVo = BasicCard.builder()
                .title("Test")
                .description("Test Description")
                .thumbnail(null)
                .profile(null)
                .social(null)
                .buttons(buttons)
                .build();

        Component basicCard = new Component();
        basicCard.setBasicCard(basicCardVo);

        ArrayList<Component> outputs = new ArrayList<Component>();
        outputs.add(basicCard);


        SkillTemplate template = new SkillTemplate();
        template.setOutputs(outputs);

        ResponseDtoVerTwo responseDtoVerTwo = ResponseDtoVerTwo.builder()
                .version("2.0")
                .template(template)
                .build();


        return responseDtoVerTwo;
    }
}
