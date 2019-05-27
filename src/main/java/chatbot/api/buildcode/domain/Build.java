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

    private String hProviderId;

    private ArrayList<Hub> hubs;

    private ArrayList<HrdwrDto> hrdwrs;
    //private HrdwrDto[] hrdwrs;

    private BoxDto box;

    private String authKey;

    private ArrayList<BtnDto> btns;

    private ArrayList<DerivationDto> derivations;

    private ArrayList<SelectedBtn> selectedBtns; // 전달할 데이터

    private Path path;                           // 전달 경로
}
