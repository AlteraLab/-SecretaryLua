package chatbot.api.common.config.rabbit.domain.pub;

import lombok.ToString;

import java.io.Serializable;

@ToString
public class EstablishResultMessage implements Serializable {

    private Boolean status;

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getStatus() {
        return this.status;
    }
}
