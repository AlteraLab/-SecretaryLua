package chatbot.api.textbox.domain.reservation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationListDTO {

    private Boolean status;

    private ArrayList<ReservationDTO> reserveList;

    private String url;
}
