package chatbot.api.textbox.domain.path;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Hub implements Serializable {

    private int hubSeq;

    private String explicitIp;

    private int explicitPort;
}
