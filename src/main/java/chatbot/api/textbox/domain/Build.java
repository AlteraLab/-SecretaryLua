package chatbot.api.textbox.domain;

import chatbot.api.textbox.domain.path.HrdwrDTO;
import chatbot.api.textbox.domain.path.Hub;
import chatbot.api.textbox.domain.path.Path;
import chatbot.api.textbox.domain.textboxdata.BoxDTO;
import chatbot.api.textbox.domain.textboxdata.BtnDTO;
import chatbot.api.textbox.domain.textboxdata.DerivationDTO;
import chatbot.api.textbox.domain.transfer.CmdList;
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

    // Redis에 저장되어 꺼내졌다 저장되어졌다 하는 도메인

    // key
    private String hProviderId;

    // path
    private Path path;                     // 전달 경로
    private ArrayList<Hub> hubs;           // 사용가능한 허브 목록
    private ArrayList<HrdwrDTO> hrdwrs;    // 사용가능한 하드웨어 목록

    // textbox data
    private ArrayList<BoxDTO> boxs;
    private ArrayList<BtnDTO> btns;
    private ArrayList<DerivationDTO> derivations;

    // 선택된 하드웨어
    private HrdwrDTO selectedHrdwr;

    // 선택된 박스
    private BoxDTO curBox;

    // 선택된 박스에 포함된 버튼 리스트
    private ArrayList<BtnDTO> curBtns;

    // 전달할 데이터
    private CmdList cmdList;
}