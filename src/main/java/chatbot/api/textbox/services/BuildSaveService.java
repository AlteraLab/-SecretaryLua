package chatbot.api.textbox.services;

import chatbot.api.mappers.BoxMapper;
import chatbot.api.mappers.BtnMapper;
import chatbot.api.mappers.DerivationMapper;
import chatbot.api.mappers.HrdwrMapper;
import chatbot.api.textbox.domain.*;
import chatbot.api.textbox.domain.blockid.BelowBlockIds;
import chatbot.api.textbox.domain.path.HrdwrDTO;
import chatbot.api.textbox.domain.path.Hub;
import chatbot.api.textbox.domain.path.Path;
import chatbot.api.textbox.domain.textboxdata.BoxDTO;
import chatbot.api.textbox.domain.textboxdata.BtnDTO;
import chatbot.api.textbox.domain.textboxdata.DerivationDTO;
import chatbot.api.textbox.domain.transfer.Additional;
import chatbot.api.textbox.domain.transfer.CmdList;
import chatbot.api.textbox.repository.BuildRepository;
import chatbot.api.skillhub.domain.HubInfoDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;
import org.springframework.stereotype.Service;

import javax.print.DocFlavor;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import static chatbot.api.textbox.utils.TextBoxConstants.BOX_TYPE_ENTRY;
import static chatbot.api.textbox.utils.TextBoxConstants.BUTTON_TYPE_CONTROL;

@Service
@Slf4j
public class BuildSaveService {

    @Autowired
    private BuildRepository buildRepository;

    @Autowired
    private HrdwrMapper hrdwrMapper;

    @Autowired
    private BoxMapper boxMapper;

    @Autowired
    private BtnMapper btnMapper;

    @Autowired
    private DerivationMapper derivationMapper;

    @Autowired
    private BuildAllocaterService buildAllocaterService;



    public void saverProviderId(String providerId) {

        log.info("=============== Saver ProviderId 시작 ===============");

        Build build = Build.builder()
                .hProviderId(providerId)
                .build();

        buildRepository.save(build);
    }

    public void saverHubs(String providerId, ArrayList<HubInfoDTO> hubs) {

        log.info("=============== Saver Hubs 시작 ===============");

        Build reBuild = buildRepository.find(providerId);

        ArrayList<Hub> arrHubs = new ArrayList<Hub>();
        Hub hub = null;
        for(int i = 0; i < hubs.size(); i++) {
            hub = Hub.builder()
                    .hubSeq(i + 1)
                  //  .hubId(hubs.get(i).getHubId())
                    .explicitIp(hubs.get(i).getExternalIp())
                    .explicitPort(hubs.get(i).getExternalPort())
                    .build();
            arrHubs.add(hub);
        }

        reBuild.setHubs(arrHubs);
        log.info("INFO >> 허브 빌드 내용 : " + reBuild.getHubs());

        buildRepository.update(reBuild);
        log.info("=============== Saver Hubs 끝    ===============");
    }



    public void saverHrdwrs(String providerId, HrdwrDTO[] hrdwrs) {

        log.info("=============== Saver Hrdwrs 시작 ===============");

        Build reBuild = buildRepository.find(providerId);

        log.info("INFO >> 받아온 DEV 목록");
        String tempHrdwrMac = null;
        for(int i = 0; i < hrdwrs.length; i++) {
            tempHrdwrMac = hrdwrs[i].getHrdwrMac();

            hrdwrs[i] = hrdwrMapper.getHrdwrByAuthKey(hrdwrs[i].getAuthKey());
            hrdwrs[i].setHrdwrSeq(i + 1);
            hrdwrs[i].setHrdwrMac(tempHrdwrMac);

            log.info(hrdwrs[i].getHrdwrSeq() + "번 : " + hrdwrs[i].toString());
        }

        reBuild.setHrdwrs(new ArrayList<HrdwrDTO>());
        for(HrdwrDTO tempHrdwr : hrdwrs) {
            reBuild.getHrdwrs().add(tempHrdwr);
        }
        buildRepository.update(reBuild);
        log.info("=============== Saver Hrdwrs 끝   ===============");
    }



    public void saverPathAboutAddr(String providerId, Path path) {
        log.info("=============== Saver Path About external IP And Port 시작 ===============");
        Build reBuild = buildRepository.find(providerId);
        reBuild.setPath(Path.builder()
                .externalIp(path.getExternalIp())
                .externalPort(path.getExternalPort())
                .build());
        reBuild.setHubs(null);
        buildRepository.update(reBuild);
        log.info("=============== Saver Path About external IP And Port 종료 ===============");
    }



    public void saverPathAboutMac(String providerId) {
        log.info("=============== Saver Path About MAC Addr 시작 ===============");
        Build reBuild = buildRepository.find(providerId);
        reBuild.getPath().setHrdwrMacAddr(reBuild.getSelectedHrdwr().getHrdwrMac());
        buildRepository.update(reBuild);
        log.info("=============== Saver Path About MAC Addr 종료 ===============");
    }


    public void saverSelectedHrdwr(String providerId, int hrdwrSeq) {
        log.info("=============== Saver Selected Hrdwr 시작 ===============");
        Build reBuild = buildRepository.find(providerId);
        reBuild.setSelectedHrdwr(reBuild.getHrdwrs().get(hrdwrSeq - 1));
        log.info("INFO >> reBuild.getHrdwrs -> " + reBuild.getHrdwrs());
        log.info("INFO >> reBuild.SelectedHrdwr -> " + reBuild.getSelectedHrdwr());
        reBuild.setHrdwrs(null);
        buildRepository.update(reBuild);
        log.info("=============== Saver Selected Hrdwr 종료 ===============");
    }


    // 명령을 빌드하는데 필요한 데이터들을 저장 - BOXS / BTNS / DERIVATIONS
    public void saverMultipleData(String providerId) {
        log.info("=============== Saver Multiple Data 시작 ===============");
        Build reBuild = buildRepository.find(providerId);
        Long hrdwrId = reBuild.getSelectedHrdwr().getHrdwrId();
        reBuild.setBoxs(boxMapper.getBoxsByHrdwrId(hrdwrId));
        reBuild.setBtns(btnMapper.getBtnsByHrdwrId(hrdwrId));
        reBuild.setDerivations(derivationMapper.getDerivationsByHrdwrId(hrdwrId));
        buildRepository.update(reBuild);
        log.info("Box List -> " + reBuild.getBoxs());
        log.info("Btn List -> " + reBuild.getBtns());
        log.info("Derivation List -> " + reBuild.getDerivations());
        log.info("=============== Saver Multiple Data 종료 ===============");
    }


    public void initCmdListWhenEntry(String providerId) {
        log.info("=============== Init CmdList 시작 ===============");
        Build reBuild = buildRepository.find(providerId);

        // init CmdList
        reBuild.setCmdList(new ArrayList<CmdList>());

        buildRepository.update(reBuild);
        log.info("=============== Init CmdList 종료 ===============");
    }


    public void initCurBoxWhenEntry(String providerId) {
        log.info("=============== Init CurBox 시작 ===============");
        Build reBuild = buildRepository.find(providerId);

        // When Create Entry Box
        // init CurBox <- Entry Box
        ArrayList<BoxDTO> boxs = reBuild.getBoxs();
        for(BoxDTO tempBox : boxs) {
            if(tempBox.getBoxType() == BOX_TYPE_ENTRY) {
                reBuild.setCurBox(tempBox);
                break;
            }
        }

        buildRepository.update(reBuild);
        log.info("=============== Init CurBox 종료 ===============");
    }


    // curBoxId만 올바로 초기화 되어 있으면, entry 박스 혹은 텍스트 박스에서도 사용할 수 있는 메소드
    public void initCurBtns(String providerId) {
        log.info("=============== Init CurBtns 시작 ===============");
        Build reBuild = buildRepository.find(providerId);
        // 현재 박스 아이디를 구한다
        Integer curBoxId = reBuild.getCurBox().getBoxId();
        log.info("Current Box ID -> " + curBoxId);

        // init CurBtns in CurBox
        reBuild.setCurBtns(new ArrayList<BtnDTO>());
        ArrayList<BtnDTO> curBtns = reBuild.getCurBtns();
        ArrayList<BtnDTO> btns = reBuild.getBtns();
        for(BtnDTO tempBtn : btns) {
            if(curBoxId == tempBtn.getBoxId()) {
                reBuild.getCurBtns().add(tempBtn);
            }
        }

        // 정렬
        Collections.sort(curBtns, new Comparator<BtnDTO>() {
            @Override
            public int compare(BtnDTO o1, BtnDTO o2) {
                return o1.getIdx().compareTo(o2.getIdx());
            }
        });

        // 정렬 확인 및 인덱스 값 증가
        for(BtnDTO tempCurBtn : curBtns) {
            tempCurBtn.setIdx(tempCurBtn.getIdx() + 1);   //  인덱스 값 증가
            log.info(tempCurBtn.getIdx().toString() + " " + tempCurBtn.toString());
        }

        buildRepository.update(reBuild);
        log.info("=============== Init CurBtns 종료 ===============");
    }


    // 객체를 할당해줌
    public void initHControlBlocks(String providerId) {
        log.info("=============== Init HControl blocks 시작 ===============");
        Build reBuild = buildRepository.find(providerId);
        reBuild.setHControlBlocks(new HashMap<Integer, BelowBlockIds>());
        buildRepository.update(reBuild);
        log.info("=============== Init HControl blocks 종료 ===============");
    }


    public void saverSelectedBtn(String providerId, Integer btnIdx) {
        log.info("=============== Save Selected Button Index 시작 ===============");
        Build reBuild = buildRepository.find(providerId);
        ArrayList<BtnDTO> curBtns = reBuild.getCurBtns();
        BtnDTO curBtn = null;
        // 선택된 버튼 찾기 및 저장
        for(BtnDTO tempBtn : curBtns) {
            if(btnIdx == tempBtn.getIdx()) {
                reBuild.setSelectedBtn(tempBtn);
                curBtn = tempBtn;
                break;
            }
        }
        log.info("Selected Button -> " + curBtn);
        ArrayList<CmdList> cmdList = reBuild.getCmdList();
        CmdList cmd = CmdList.builder()
                .btnType(curBtn.getBtnType())
                .eventCode(curBtn.getEventCode())
                .additional(null)
                .build();
        cmdList.add(cmd);
        buildRepository.update(reBuild);
        log.info("=============== Save Selected Button Index 종료 ===============");
    }


    public void initSelectedBtnToNull(String providerId) {
        log.info("=============== Init Selected Button To Null 시작 ===============");
        Build reBuild = buildRepository.find(providerId);
        reBuild.setSelectedBtn(null);
        buildRepository.update(reBuild);
        log.info("=============== Init Selected Button To Null 종료 ===============");
    }

    public void saverTimeStamp(String providerId, Timestamp timestamp) {
        log.info("=============== Save TimeStamp 시작 ===============");
        Build reBuild = buildRepository.find(providerId);
        Additional additional = Additional.builder()
                .type('1') // time
                .value(timestamp)
                .build();

        ArrayList<CmdList> cmdList = reBuild.getCmdList();
        CmdList cmd = cmdList.get(cmdList.size() - 1);
       // cmd.setAdditional(new ArrayList<Additional>());
        cmd.getAdditional().add(additional);
        log.info("Save Cmd -> " + reBuild.getCmdList().get(cmdList.size() - 1));
        buildRepository.update(reBuild);
        log.info("=============== Save TimeStamp 종료 ===============");
    }

    public void initAdditional(String providerId) {
        log.info("=============== Save Additional 시작 ===============");
        Build reBuild = buildRepository.find(providerId);
        ArrayList<CmdList> cmdList = reBuild.getCmdList();
        CmdList cmd = cmdList.get(cmdList.size() - 1);
        cmd.setAdditional(new ArrayList<Additional>());
        buildRepository.update(reBuild);
        log.info("=============== Save Additional 종료 ===============");
    }


    // HControlBlocksIds 를 null 로 만들어서 redis 데이터를 보기 편하게 만듬
    public void initHControlBlocksToNull(String providerId) {
        log.info("=============== Init HControl Blocks Ids To NULL 시작 ===============");
        Build reBuild = buildRepository.find(providerId);
        reBuild.setHControlBlocks(null);
        buildRepository.update(reBuild);
        log.info("=============== Init HControl Blocks Ids To NULL 종료 ===============");
    }


    public void saverDynamicValue(String providerId, Integer dynamicValue) {
        log.info("=============== Save Dynamic Value 시작 ===============");
        Build reBuild = buildRepository.find(providerId);
        ArrayList<CmdList> cmdList = reBuild.getCmdList();
        CmdList cmd = cmdList.get(cmdList.size() - 1);

        Additional dynamicAdditional = Additional.builder()
                .type('2') // dynamic
                .value(dynamicValue)
                .build();

        cmd.getAdditional().add(dynamicAdditional);
        log.info("Dynamic Additional -> " + dynamicAdditional);
        log.info("Cmd -> " + cmd);
        buildRepository.update(reBuild);
        log.info("=============== Save Dynamic Value 종료 ===============");
    }

    // 현재 박스 조정
    public void saverCurBoxWhenEndFrDynamicFrTimeFrEntry(String providerId) {
        log.info("=============== Save Current Box When End From Dynamic From Time From Entry 시작 ===============");
        Build reBuild = buildRepository.find(providerId);

        ArrayList<DerivationDTO> derivations = reBuild.getDerivations();
        BoxDTO curBox = reBuild.getCurBox();
        BoxDTO lowerBox = null;
        for(DerivationDTO tempDerivation : derivations) {
            if(curBox.getBoxId() == tempDerivation.getUpperBoxId()) {
                lowerBox = buildAllocaterService.allocateBoxByBoxId(providerId, tempDerivation.getLowerBoxId());
            }
        }
        reBuild.setCurBox(lowerBox);
        buildRepository.update(reBuild);
        log.info("=============== Save Current Box When End From Dynamic From Time From Entry 종료 ===============");
    }

    public void saverCurBoxWhenEndFrTimeFrUDynamicFrEntry(String providerId) {
        log.info("=============== Save Current Box When End From Time From _Dynamic From Entry 시작 ===============");
        // 현재 상태가 동적 박스이니까, 시간 박스 -> End or Control Box 로 만들어야 한다
        Build reBuild = buildRepository.find(providerId);

        ArrayList<DerivationDTO> derivations = reBuild.getDerivations();
        BoxDTO curBox = reBuild.getCurBox();
        BoxDTO lowerBox = null;
        for(DerivationDTO tempDerivation : derivations) {
            if(curBox.getBoxId() == tempDerivation.getUpperBoxId()) {
                curBox = buildAllocaterService.allocateBoxByBoxId(providerId, tempDerivation.getLowerBoxId());
            }
        }
        for(DerivationDTO tempDerivation : derivations) {
            if(curBox.getBoxId() == tempDerivation.getUpperBoxId()) {
                lowerBox = buildAllocaterService.allocateBoxByBoxId(providerId, tempDerivation.getLowerBoxId());
            }
        }
        reBuild.setCurBox(lowerBox);
        buildRepository.update(reBuild);
        log.info("=============== Save Current Box When End From Time From _Dynamic From Entry 종료 ===============");
    }

    // additional 을 추가하는 제어 명령을 빌드하는 시나리오일 경우 사용하는 함수
    // 하위 박스를 체크하고, 있다면 curBox 를 하위 박스로 초기화, 없으면 curBox 를 null 로 초기화
    public void saverCurBoxWhenEndFrEntry(String providerId) {
        log.info("=============== Save Current Box When End From Entry 시작 ===============");
        Build reBuild = buildRepository.find(providerId);
        ArrayList<DerivationDTO> derivations = reBuild.getDerivations();
        BoxDTO curBox = reBuild.getCurBox();
        BtnDTO selectedBtn = reBuild.getSelectedBtn();
        BoxDTO lowerBox = null;
        for(DerivationDTO tempDerivation : derivations) {
            if(curBox.getBoxId() == tempDerivation.getUpperBoxId() &&
            selectedBtn.getBtnCode() == tempDerivation.getBtnCode()) {
                lowerBox = buildAllocaterService.allocateBoxByBoxId(providerId, tempDerivation.getLowerBoxId());
            }
        }
        reBuild.setCurBox(lowerBox);
        buildRepository.update(reBuild);
        log.info("=============== Save Current Box When End From Entry 종료 ===============");
    }


    public void saverCurBoxWhenEndFrTimeFrEntry(String providerId) {
        log.info("=============== Save Current Box When End From Time From Entry 시작 ===============");
        // CurBox 가 Entry Box 이기 때문에 두 단계 아래 박스로 조정해야함

        Build reBuild = buildRepository.find(providerId);
        ArrayList<DerivationDTO> derivations = reBuild.getDerivations();
        BoxDTO curBox = reBuild.getCurBox();
        BtnDTO selectedBtn = reBuild.getSelectedBtn();
        BoxDTO lowerBox = null;

        for(DerivationDTO tempDerivation : derivations) {
            if(curBox.getBoxId() == tempDerivation.getUpperBoxId() &&
                    selectedBtn.getBtnCode() == tempDerivation.getBtnCode()) {
                curBox = buildAllocaterService.allocateBoxByBoxId(providerId, tempDerivation.getLowerBoxId());
            }
        }
        for(DerivationDTO tempDerivation : derivations) {
            if(curBox.getBoxId() == tempDerivation.getUpperBoxId()) {
                lowerBox = buildAllocaterService.allocateBoxByBoxId(providerId, tempDerivation.getLowerBoxId());
            }
        }
        reBuild.setCurBox(lowerBox);
        buildRepository.update(reBuild);
        log.info("=============== Save Current Box When End From Time From Entry 종료 ===============");
    }

    public void saverCurBoxWhenEndFrDynamicFrEntry(String providerId) {
        log.info("=============== Save Current Box When End From Dynamic From Entry 시작 ===============");
        // CurBox 가 Dynamic Box 이기 때문에 한 단계 아래 박스로 조정해야함

        Build reBuild = buildRepository.find(providerId);
        ArrayList<DerivationDTO> derivations = reBuild.getDerivations();
        BoxDTO curBox = reBuild.getCurBox();
        BoxDTO lowerBox = null;

        for(DerivationDTO tempDerivation : derivations) {
            if(curBox.getBoxId() == tempDerivation.getUpperBoxId()) {
                lowerBox = buildAllocaterService.allocateBoxByBoxId(providerId, tempDerivation.getLowerBoxId());
            }
        }
        reBuild.setCurBox(lowerBox);
        buildRepository.update(reBuild);
        log.info("=============== Save Current Box When End From Dynamic From Entry 종료 ===============");
    }
}




/*
    // 이건 entry box에만 적용되는게 아님. makerTextCard 메소드를 만들거고
    // makerTextCard 에서는 curbox를 먼저 구하는 함수를 쓰고, saverCurBtns 함수를 쓸거임.
    public void saverCurBtns(String providerId) {

        log.info("=============== Saver Current Btns 시작 ===============");

        Build reBuild = buildRepository.find(providerId);

        Integer curBoxId = reBuild.getCurBox().getBoxId();
        ArrayList<BtnDTO> btns = reBuild.getBtns();
        ArrayList<BtnDTO> curBtns = reBuild.getCurBtns();

        // 정렬
        Collections.sort(curBtns, new Comparator<BtnDTO>() {
            @Override
            public int compare(BtnDTO o1, BtnDTO o2) {
                return o1.getIdx().compareTo(o2.getIdx());
            }
        });
        // 정렬 확인
        log.info("정렬 확인 정렬 확인 정렬 확인 정렬 확인 정렬 확인 정렬 확인 정렬 확인 정렬 확인 정렬 확인 정렬 확인 정렬 확인 정렬 확인 ");
        for(BtnDTO temp : curBtns) {
            temp.setIdx(temp.getIdx() + 1);
            log.info(temp.getIdx().toString());
        }

        for(BtnDTO tempBtn : btns) {
            if(tempBtn.getBoxId() == curBoxId) {
                curBtns.add(tempBtn);
            }
        }

        buildRepository.update(reBuild);

        log.info("=============== Saver Current Btns 종료 ===============");
    }*/