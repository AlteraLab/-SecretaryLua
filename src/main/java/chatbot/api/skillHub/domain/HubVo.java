package chatbot.api.skillHub.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@SuppressWarnings("ALL")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HubVo {

    // layer 간 데이터 교환

    private Long hubId;

    private Long adminId;

    // min 설정

    @Size(max = 16, message = "허브 이름은 16자를 넘을 수 없습니다.")
    @JsonProperty("hub_name")
    @NotNull
    private String name;

    @Size(max = 16, message = "허브 설명은 16자를 넘을 수 없습니다.")
    @JsonProperty("hub_descript")
    private String desc;

    @Size(max = 20, message = "검색용 아이디는 20자를 넘을 수 없습니다.")
    @JsonProperty("search_id")
    @NotNull
    private String searchId;

    @Size(max = 17, message = "유효하지 못한 MAC Address 입니다.")
    @JsonProperty("mac_addr")
    @NotNull
    private String macAddr;

    @Size(max = 15, message = "유효하지 못한 Ip 입니다.")
    @JsonProperty("external_ip")
    @NotNull
    private String externalIp;

    @JsonProperty("external_port")
    @NotNull
    private int externalPort;

    private String internalIp;

    private int internalPort;

    @Size(min = 15, max = 15, message = "유효하지 못한 Ip 입니다.")
    @JsonProperty("before_ip")
    private String beforeIp;

    private Timestamp lastUsedTime;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    private boolean state;

    private String role;
}
