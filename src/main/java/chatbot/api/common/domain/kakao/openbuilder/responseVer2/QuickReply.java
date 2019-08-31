package chatbot.api.common.domain.kakao.openbuilder.responseVer2;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuickReply implements Cloneable {

    // 사용자에게 노출될 바로가기 응답의 표시
    private String label;

    // 바로가기 응답의 기능, 'message' or 'block'
    private String action;

    // 사용자 측으로 노출될 발화
    private String messageText;

    // 바로가기 응답이 '블록연결' 기능일 때 연결된 블록의 id
    private String blockId;     // action이 'block'일 때, 필수값

    // 블록을 호출 시 추가적으로 제공하는 정보
    private Object extra;



    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
