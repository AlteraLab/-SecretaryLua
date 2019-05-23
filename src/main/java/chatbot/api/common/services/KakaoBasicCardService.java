package chatbot.api.common.services;

import chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseDtoVerTwo;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.component.basicCard.BasicCard;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.component.basicCard.Button;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.component.basicCard.ComponentBasicCard;
import chatbot.api.common.domain.kakao.openbuilder.responseVer2.SkillTemplate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
@Slf4j
@AllArgsConstructor
public class KakaoBasicCardService {

    private KakaoSimpleTextService kakaoSimpleTextService;



    public ResponseDtoVerTwo responserRequestJoin(String id) {
        ArrayList<Button> buttons = new ArrayList<Button>();
        buttons.add(Button.builder()
                .action("webLink")
                .label("Sign Up")
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

        ComponentBasicCard basicCard = new ComponentBasicCard();
        basicCard.setBasicCard(basicCardVo);

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


    // 사용자가 발화를 넘겼을때 appUserId가 null 일때 회원가입 링크를 response 해준다.
    // 아직 구현 안해줌.
    public ResponseDtoVerTwo responserRequestPreSignUp() {

        ArrayList<Button> buttons = new ArrayList<Button>();
        buttons.add(Button.builder()
                .action("webLink")
                .label("Sign Up")
                .webLinkUrl("http://203.250.32.29:3000/")  // 가입 링크
                .build());

        BasicCard basicCardVo = BasicCard.builder()
                .title("Sign Up")
                .description("회원가입을 먼저 하세요.")
                .thumbnail(null)
                .profile(null)
                .social(null)
                .buttons(buttons)
                .build();

        ComponentBasicCard basicCard = new ComponentBasicCard();
        basicCard.setBasicCard(basicCardVo);

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
}

