package chatbot.api.buildcode.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Build implements Serializable {

    private String hProviderId;            // Redis 키 값

    private Path path;                     // 전달 경로

    private ArrayList<Hub> hubs;           // 사용가능한 허브 목록

    private ArrayList<HrdwrDTO> hrdwrs;    // 사용가능한 하드웨어 목록
    //private HrdwrDto[] hrdwrs;

    private BoxDTO box;

    private String authKey;

    private ArrayList<BtnDTO> btns;

    private ArrayList<DerivationDTO> derivations;

    private ArrayList<SelectedBtn> selectedBtns; // 전달할 데이터
}
