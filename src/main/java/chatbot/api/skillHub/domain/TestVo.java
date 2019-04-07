package chatbot.api.skillHub.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestVo {

    @Size(max = 16, message = "허브 이름은 16자를 넘을 수 없습니다.")
    @NotNull
    @JsonProperty("hub_name")
    private String name;

    @Size(max = 16, message = "허브 설명은 16자를 넘을 수 없습니다.")
    @JsonProperty("hub_descript")
    private String desc;

    @Size(max = 20, message = "검색용 아이디는 20자를 넘을 수 없습니다.")
    @NotNull
    @JsonProperty("search_id")
    private String searchId;

    @Size(max = 17, message = "유효하지 못한 MAC Address 입니다.")
    @NotNull
    @JsonProperty("mac_addr")
    private String macAddr;

    @Size(max = 15, message = "유효하지 못한 Ip 입니다.")
    @NotNull
    @JsonProperty("external_ip")
    private String externalIp;

    @NotNull
    @JsonProperty("external_port")
    private int externalPort;

    @Size(max = 15, message = "유효하지 못한 Ip 입니다.")
    @JsonProperty("before_ip")
    private String beforeIp;
}
