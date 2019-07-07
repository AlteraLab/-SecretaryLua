package chatbot.api.textbox.services;

import chatbot.api.mappers.BoxMapper;
import chatbot.api.mappers.BtnMapper;
import chatbot.api.mappers.DerivationMapper;
import chatbot.api.mappers.HrdwrMapper;
import chatbot.api.textbox.domain.*;
import chatbot.api.textbox.domain.path.HrdwrDTO;
import chatbot.api.textbox.domain.path.Hub;
import chatbot.api.textbox.domain.path.Path;
import chatbot.api.textbox.domain.textboxdata.BoxDTO;
import chatbot.api.textbox.domain.textboxdata.BtnDTO;
import chatbot.api.textbox.domain.textboxdata.DerivationDTO;
import chatbot.api.textbox.domain.transfer.CmdList;
import chatbot.api.textbox.repository.BuildRepository;
import chatbot.api.skillhub.domain.HubInfoDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;
import org.springframework.stereotype.Service;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static chatbot.api.textbox.utils.TextBoxConstants.BOX_TYPE_ENTRY;

@Service
@AllArgsConstructor
@Slf4j
public class BuildSaveService {

    private BuildRepository buildRepository;

    private HrdwrMapper hrdwrMapper;

    private BoxMapper boxMapper;

    private BtnMapper btnMapper;

    private DerivationMapper derivationMapper;



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
        reBuild.setCmdList(new CmdList());

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


    public void initCurBtns(String providerId) {
        log.info("=============== Init CurBtns 시작 ===============");
        Build reBuild = buildRepository.find(providerId);

        Integer curBoxId = reBuild.getCurBox().getBoxId();
        log.info("Current Box ID -> " + curBoxId);
        // init CurBtns in CurBox
        reBuild.setCurBtns(new ArrayList<BtnDTO>());
        for(BtnDTO tempBtn : reBuild.getCurBtns()) {
            if(curBoxId == tempBtn.getBoxId()) {
                reBuild.getCurBtns().add(tempBtn);
            }
        }

        buildRepository.update(reBuild);
        log.info("=============== Init CurBtns 종료 ===============");
    }



    // 이건 entry box에만 적용되는게 아님. makerTextCard 메소드를 만들거고
    // makerTextCard 에서는 curbox를 먼저 구하는 함수를 쓰고, saverCurBtns 함수를 쓸거임. 그 이후에 필터
    public void saverCurBtns(String providerId) {

        log.info("=============== Saver Current Btns 시작 ===============");

        Build reBuild = buildRepository.find(providerId);

        BoxDTO curBox = reBuild.getCurBox();
        Integer curBoxId = curBox.getBoxId();

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
            log.info(temp.getIdx().toString());
        }


        for(BtnDTO temp : btns) {
            if(temp.getBoxId() == curBoxId) {
                curBtns.add(temp);
            }
        }

        buildRepository.update(reBuild);

        log.info("=============== Saver Current Btns 종료 ===============");
    }
}