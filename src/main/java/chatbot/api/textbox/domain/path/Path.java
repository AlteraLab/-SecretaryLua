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
public class Path implements Serializable {

    private String externalIp;

    private int externalPort;

    private String hrdwrMacAddr;
}
