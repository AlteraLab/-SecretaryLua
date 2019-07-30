package chatbot.api.textbox.domain.transfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CmdReservationDeletion {

    private Character btnType;

    private Integer eventCode;

    private Integer additional; // resId for deletion
}
