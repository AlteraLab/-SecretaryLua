package chatbot.api.textbox.domain.response;

import chatbot.api.textbox.domain.path.HrdwrDTO;
import lombok.*;
import org.codehaus.jackson.annotate.JsonProperty;


@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseHrdwrInfo {

    @Getter @Setter
    private boolean status;

    @JsonProperty("devInfo")
    private HrdwrDTO[] hrdwrsInfo;



    public HrdwrDTO[] getDevInfo() {
        return hrdwrsInfo;
    }

    public void setDevInfo(HrdwrDTO[] hrdwrsInfo) {
        this.hrdwrsInfo = hrdwrsInfo;
    }

    public Boolean hasNull() {
        if(this.getDevInfo() == null) return true;
        else                          return false;
    }
}
