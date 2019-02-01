package chatbot.api.common.domain.kakao.openbuilder;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Action {
	private String name;
	private Object clientExtra;
	private Object params;
	private String id;
	private Object detailParams;
}
