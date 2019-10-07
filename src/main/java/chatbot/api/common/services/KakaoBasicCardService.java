package chatbot.api.common.services;

import chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseVerTwoDTO;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.component.basicCard.BasicCard;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.component.basicCard.Button;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.component.basicCard.ComponentBasicCard;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.SkillTemplate;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
@Slf4j
public class KakaoBasicCardService {

    private KakaoSimpleTextService kakaoSimpleTextService;


    // 사용자가 발화를 넘겼을때 appUserId가 null 일때 회원가입 링크를 response 해준다.
    public ResponseVerTwoDTO responserRequestPreSignUp() {

        ArrayList<Button> buttons = new ArrayList<Button>();
        buttons.add(Button.builder()
                .action("webLink")
                .label("S I B A")
                .webLinkUrl("http://203.250.32.29:3000/")  // 가입 링크
                .build());

        BasicCard basicCardVo = BasicCard.builder()
                .title("Sign Up")
                .description("\n가입되어 있지 않은 사용자 입니다." +
                        "\n\n카카오 계정을 이용하여사용자 가입 후 SIBA 허브를 추가해주세요.")
                .buttons(buttons)
                .build();

        ComponentBasicCard basicCard = new ComponentBasicCard();
        basicCard.setBasicCard(basicCardVo);

        ArrayList<Object> outputs = new ArrayList<Object>();
        outputs.add(basicCard);

        SkillTemplate template = new SkillTemplate();
        template.setOutputs(outputs);

        ResponseVerTwoDTO responseVerTwoDTO = ResponseVerTwoDTO.builder()
                .version("2.0")
                .template(template)
                .build();

        return responseVerTwoDTO;
    }


    public ResponseVerTwoDTO responserSibaWebLink() {

        ArrayList<Button> buttons = new ArrayList<Button>();
        buttons.add(Button.builder()
                .action("webLink")
                .label("S I B A")
                .webLinkUrl("http://203.250.32.29:3000/")  // 가입 링크
                .build());

        BasicCard basicCardVo = BasicCard.builder()
                .title("SIBA Web Link")
                .description(null)
                .buttons(buttons)
                .build();

        ComponentBasicCard basicCard = new ComponentBasicCard();
        basicCard.setBasicCard(basicCardVo);

        ArrayList<Object> outputs = new ArrayList<Object>();
        outputs.add(basicCard);

        SkillTemplate template = new SkillTemplate();
        template.setOutputs(outputs);

        ResponseVerTwoDTO responseVerTwoDTO = ResponseVerTwoDTO.builder()
                .version("2.0")
                .template(template)
                .build();

        return responseVerTwoDTO;
    }
}

