package chatbot.api.common.domain.kakao.openbuilder;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Block {
	private String id;
	private String name; //블럭 이름
}
/*
"block": {
	"id": "j0zya4e7fi648hkvunoqlsep",
	"name": "블록 이름"
}
 */