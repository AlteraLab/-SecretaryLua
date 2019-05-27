package chatbot.api.buildcode.domain.response;

import chatbot.api.buildcode.domain.HrdwrDto;
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
    private HrdwrDto[] hrdwrsInfo;

    public HrdwrDto[] getDevInfo() {
        return hrdwrsInfo;
    }

    public void setDevInfo(HrdwrDto[] hrdwrsInfo) {
        this.hrdwrsInfo = hrdwrsInfo;
    }
}
