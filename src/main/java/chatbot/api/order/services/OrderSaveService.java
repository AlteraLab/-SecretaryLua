package chatbot.api.order.services;

import chatbot.api.order.domain.CmdOrder;
import chatbot.api.order.domain.DevOrder;
import chatbot.api.order.domain.HubOrder;
import chatbot.api.order.domain.MainOrder;
import chatbot.api.order.repository.MainOrderRepositoryImpl;
import chatbot.api.skillHub.domain.HubInfoDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
@Slf4j
public class OrderSaveService {

    private MainOrderRepositoryImpl mainOrderRepository;



    // mainOrder (유지되어야 할 데이터를 담음) In-Memory에 저장
    // hubs 데이터를 저장
    public void saverHubOrders(String providerId, ArrayList<HubInfoDto> hubs) {

        MainOrder mainOrder = MainOrder.builder()
                .hProviderId(providerId)
                .build();

        // mainOrder 데이터 set
        HubOrder[] hubOrders = new HubOrder[hubs.size()];
        HubOrder hubOrder = null;
        for(int i = 0; i < hubs.size(); i++) {
            hubOrder = HubOrder.builder()
                    .hubSeq(i + 1)
                    .hubId(hubs.get(i).getHubId())
                    .explicitIp(hubs.get(i).getExternalIp())
                    .explicitPort(hubs.get(i).getExternalPort())
                    .build();
            hubOrders[i] = hubOrder;
        }
        mainOrder.setHubOrderList(hubOrders);
        log.info("MainOrders : " + mainOrder.toString());

        // redis 에 저장
        mainOrderRepository.update(mainOrder);
    }


    // mainOrder (유지되어야 할 데이터를 담음) In-Memory에 저장
    // devs 데이터를 저장
    public void saverDevOrders(String providerId, DevOrder [] devs) {

        MainOrder reMainOrder = mainOrderRepository.find(providerId);

        for(int i = 0; i < devs.length; i++) {
            devs[i].setDevSeq(i + 1);
            log.info(i + 1 + "번 : " + devs[i].toString());
        }
        reMainOrder.setDevOrderList(devs);
        log.info(reMainOrder.toString());

        mainOrderRepository.update(reMainOrder);
    }



    // mainOrder (유지되어야 할 데이터를 담음) In-Memory에 저장
    // cmds 데이터를 저장
    public void saverCmdOrders(String providerId, CmdOrder[] cmds) {

        MainOrder reMainOrder = mainOrderRepository.find(providerId);

        reMainOrder.setCmdOrderList(cmds);
        log.info("redis에 저장된 데이터 확인 >> " + reMainOrder.toString());

        mainOrderRepository.save(reMainOrder);
    }
}
