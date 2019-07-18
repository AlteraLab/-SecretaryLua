package chatbot.api.common.config.rabbit.domain.sub;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.ToString;

import java.io.Serializable;

@ToString
public class EstablishMessage implements Serializable {

    private String ip;

    private Integer port;

    private String mac;


    public String getIp() {
        return ip;
    }

    public Integer getPort() {
        return port;
    }

    public String getMac() {
        return mac;
    }
}
