package chatbot.api.test;

import chatbot.api.build.domain.BoxDto;

import chatbot.api.build.domain.BtnDto;
import chatbot.api.build.domain.DerivationDto;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.component.basicCard.ComponentBasicCard;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseDtoVerTwo;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.component.basicCard.BasicCard;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.component.basicCard.Button;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.SkillTemplate;
import chatbot.api.mappers.BoxMapper;
import chatbot.api.mappers.BtnMapper;
import chatbot.api.mappers.DerivationMapper;
import chatbot.api.mappers.HrdwrMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;


@Slf4j
@RestController
public class TestController {

    @Autowired
    private BoxMapper boxMapper;

    @Autowired
    private BtnMapper btnMapper;

    @Autowired
    private DerivationMapper derivationMapper;

    @Autowired
    private HrdwrMapper hrdwrMapper;


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

        ComponentBasicCard basicCard = new ComponentBasicCard();
        basicCard.setBasicCard(basicCardVo);

        //ArrayList<Component> outputs = new ArrayList<Component>();
        ArrayList<Object> outputs = new ArrayList<Object>();
        outputs.add(basicCard);


        SkillTemplate template = new SkillTemplate();
        template.setOutputs(outputs);

        ResponseDtoVerTwo responseDtoVerTwo = ResponseDtoVerTwo.builder()
                .version("2.0")
                .template(template)
                .build();


        return responseDtoVerTwo;
    }

    /*
    @PostMapping("/test/authKey")
    public Object testGetBoxByAuthKey() {
        log.info("시작");

        // authKey = "AAAABBBBCCCCDDDDEEEEFFFFGGGGHHHH";
        String authKey = hrdwrMapper.getAuthKeyByUserDefinedName("LG AC01");
        log.info("INFO >> " + authKey);


        //BoxDto bbb = boxMapper.getEntryBoxByUsrDfinName("LG AC01");
       // log.info("INFO >> " + bbb.toString());

        return new Object(){
            public BoxDto BOX = bbb;
        };
    }
    */
}
