package chatbot.api.textbox.domain.reservation;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {

    @JsonAlias("res_id")
    private Integer reservationId;

    @JsonAlias("ev_code")
    private Integer eventCode;

    @JsonAlias("act_at")
    private Timestamp actionAt;
}
