package chatbot.api.skillhub.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResultAboutReservationDelete {

    private HttpStatus status;

    private String msg;

    public void setStatus(int status) {
        this.status = HttpStatus.valueOf(status);
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
