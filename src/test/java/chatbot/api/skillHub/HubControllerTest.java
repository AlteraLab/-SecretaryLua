
package chatbot.api.skillHub;

        import chatbot.api.skillHub.services.HubDeleter;
        import org.junit.Before;
        import org.junit.Rule;
        import org.junit.Test;
        import org.junit.runner.RunWith;
        import org.mockito.Mock;
        import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
        import org.springframework.boot.test.context.SpringBootTest;
        import org.springframework.restdocs.JUnitRestDocumentation;
        import org.springframework.test.context.junit4.SpringRunner;
        import org.springframework.test.web.servlet.MockMvc;
        import org.springframework.test.web.servlet.ResultActions;
        import org.springframework.test.web.servlet.setup.MockMvcBuilders;

        import static chatbot.api.utils.ApiDocumentUtils.getDocumentRequest;
        import static chatbot.api.utils.ApiDocumentUtils.getDocumentResponse;
        import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
        import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
        import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
        import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "docs.api.com")
public class HubControllerTest {

    // Rule 어노테이션은 테스트 케이스 내에서 동작을 유연하게 추가하거나 재정의 할 수 있는 목적으로 만들어짐
    // JUnitRestDocumentation : generating documentation snippets
    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    // 브라우저에서 요청과 응답을 의미하는 객체
    private MockMvc mockMvc;

    @Mock
    private HubDeleter hubDeleter;


    // 테스트 코드 실행 전에 mockMvc 빌드
    // 이렇게 설정하면 mockMvc는 문서를 생성할 수 있는 mockMvc가 된다.
    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(HubController.class)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }


    @Test
    public void deleteHub_by_hubSeq_and_userPrincipal() throws Exception {

        // when, mockMvc.perform은 ResultActions 객체를 반환
        ResultActions result = this.mockMvc.perform(
                        delete("/hub/5")
        );

        // then
        result.andExpect(status().isOk())
                .andDo(document("explicit-delete-hub",
                        getDocumentRequest(),
                        getDocumentResponse()
                ));
    }
}