package chatbot.api.textbox.domain.transfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Additional {

    private Character type;
    // '1' : time
    // '2' : dynamic
    private Timestamp timeVar;

    private Integer dynamicVar;
}
