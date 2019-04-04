package chatbot.api.response.services;

import chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseJsonFormat.Component.basicCard.Component;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseDtoVerTwo;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseJsonFormat.Component.basicCard.BasicCard;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.SkillTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AlreadyJoinedResponser {


    public ResponseDtoVerTwo responser() {

        BasicCard basicCardVo = BasicCard.builder()
                .title("이미 등록된 사용자 입니다.")
                .description("중복 등록할 수 없습니다.")
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
