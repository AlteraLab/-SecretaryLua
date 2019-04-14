package chatbot.api.common.domain.kakao.openbuilder;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class Action {
	private String name;
	private String id;
	private Map<String, Object> clientExtra;
	private Map<String, String> params;
	private Map<String, Object> detailParams;
}
/*
action": {
	"name": "co2gpryko8",
	"clientExtra": null,
	"params": {},
	"id": "s7g0ac2qew6uj034b8b67ae0",
	"detailParams": {}
}
*/
