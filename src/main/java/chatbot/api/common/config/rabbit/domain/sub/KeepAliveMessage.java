package chatbot.api.common.config.rabbit.domain.sub;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.ToString;

import java.io.Serializable;

@ToString
public class KeepAliveMessage implements Serializable {

    private String mac;

    public KeepAliveMessage() {
    }

    public KeepAliveMessage(@JsonAlias("mac") String mac) {
        this.mac = mac;
    }

    public String getMac() {
        return mac;
    }
}
