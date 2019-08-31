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

    private Object value;
    /*
    '1' -> 1 회
    '2' -> 매일
    '3' -> 매주
     */
}
