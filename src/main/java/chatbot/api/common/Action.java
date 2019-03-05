package chatbot.api.common;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Action {
	private String name;
	private Object clientExtra;
	private Object params;                // 구현해줘야 할듯
	private String id;
	private Object detailParams;          // 구현해줘야 할듯
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
