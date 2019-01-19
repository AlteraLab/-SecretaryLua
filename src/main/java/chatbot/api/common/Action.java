package chatbot.api.common;

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
/*
action": {
	"name": "co2gpryko8",
	"clientExtra": null,
	"params": {},
	"id": "s7g0ac2qew6uj034b8b67ae0",
	"detailParams": {}
}
*/
