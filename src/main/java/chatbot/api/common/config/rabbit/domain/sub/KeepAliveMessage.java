package chatbot.api.common.config.rabbit.domain.sub;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.ToString;

import java.io.Serializable;

@ToString
public class KeepAliveMessage implements Serializable {

    private String mac;

    public String getMac() {
        return mac;
    }
}
