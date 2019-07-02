package chatbot.api.buildcode.services;

import chatbot.api.buildcode.domain.*;
import chatbot.api.buildcode.repository.BuildRepository;
import chatbot.api.skillhub.domain.HubInfoDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
@Slf4j
public class BuildSaveService {

    private BuildRepository buildRepository;



    public void saverHubs(String providerId, ArrayList<HubInfoDTO> hubs) {

        log.info("=============== Saver Hubs 시작 ===============");

        Build build = Build.builder()
                .hProviderId(providerId)
                .build();

        ArrayList<Hub> arrHubs = new ArrayList<Hub>();
        Hub hub = null;
        for(int i = 0; i < hubs.size(); i++) {
            hub = Hub.builder()
                    .hubSeq(i + 1)
                    .hubId(hubs.get(i).getHubId())
                    .explicitIp(hubs.get(i).getExternalIp())
                    .explicitPort(hubs.get(i).getExternalPort())
                    .build();
            arrHubs.add(hub);
        }

        build.setHubs(arrHubs);
        log.info("INFO >> 허브 빌드 내용 : " + build.getHubs());

        buildRepository.save(build);

        log.info("=============== Saver Hubs 끝    ===============");
    }



    public void saverHrdwrs(String providerId, HrdwrDTO[] hrdwrs) {

        log.info("=============== Saver Hrdwrs 시작 ===============");

        Build reBuild = buildRepository.find(providerId);

        log.info("INFO >> 받아온 DEV 목록");
        for(int i = 0; i < hrdwrs.length; i++) {
            hrdwrs[i].setHrdwrSeq(i + 1);
            log.info(i + 1 + "번 : " + hrdwrs[i].toString());
        }

        reBuild.setHrdwrs(new ArrayList<HrdwrDTO>());
        for(HrdwrDTO tempHrdwr : hrdwrs) {
            reBuild.getHrdwrs().add(tempHrdwr);
        }

        buildRepository.update(reBuild);

        log.info("=============== Saver Hrdwrs 끝   ===============");
    }



    public void saverBtns(String providerId, ArrayList<BtnDTO> btns) {

        log.info("=============== Saver Btns 시작 ===============");

        log.info("INFO >> 조회된 버튼 목록");
        for(int i = 0; i < btns.size(); i++) {
            btns.get(i).setBtnSeq(i + 1);
            log.info((i + 1) + "번 버튼 -> " + btns.get(i).toString());
        }

        Build reBuild = buildRepository.find(providerId);
        reBuild.setBtns(btns);
        buildRepository.update(reBuild);

        log.info("=============== Saver Btns 끝   ===============");
    }



    public void saverDerivation(String providerId, ArrayList<DerivationDTO> derivations) {

        log.info("=============== Saver Derivations 시작 ===============");

        log.info("INFO >> 조회된 파생 목록");
        for(int i = 0; i < derivations.size(); i++) {
            log.info((i + 1) + "번 파생 -> " + derivations.get(i).toString());
        }

        Build reBuild = buildRepository.find(providerId);
        reBuild.setDerivations(derivations);
        buildRepository.update(reBuild);

        log.info("=============== Saver Derivations 끝  ===============");
    }



    public void saverBox(String providerId, BoxDTO box) {

        log.info("=============== Saver Box 시작 ===============");
        log.info("INFO >> BOX 데이터 확인 -> " + box.toString());

        Build reBuild = buildRepository.find(providerId);
        reBuild.setBox(box);
        buildRepository.update(reBuild);
        log.info("=============== Saver Box 끝   ===============");
    }
}
