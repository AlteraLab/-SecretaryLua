package chatbot.api.textbox.domain.blockid;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BelowBlockIds {

    private String blockIdOnebelow;

    private String blockIdTwobelow;
}
