package chatbot.api.response.services;

import chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseJsonFormat.Component.basicCard.Component;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseDtoVerTwo;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseJsonFormat.Component.basicCard.BasicCard;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseJsonFormat.Component.basicCard.Button;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.SkillTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class RequestJoinResponser {

    public ResponseDtoVerTwo responser(String id) {

        ArrayList<Button> buttons = new ArrayList<Button>();
        buttons.add(Button.builder()
                .action("webLink")
                .label("Sign Up")
                //.webLinkUrl("https://arabiannight.tistory.com/entry/324")
                .webLinkUrl("http://203.250.32.29:3000?id=" + id)
                .build());
        BasicCard basicCardVo = BasicCard.builder()
                .title("등록")
                .description("버튼을 클릭하세요.\n" + "웹페이지에서 폼을 입력하시고 확인 버튼을 누르세요.")
                .thumbnail(null)
                .profile(null)
                .social(null)
                .buttons(buttons)
                .build();

        // test
        System.out.println(buttons.get(0).getWebLinkUrl());

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
