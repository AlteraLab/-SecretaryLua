package chatbot.api.hub.services;

import chatbot.api.hub.domain.HubInfoDto;
import chatbot.api.hub.domain.UserHubDto;
import chatbot.api.mappers.HubMapper;
import chatbot.api.mappers.UserHubMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HubRegister {

    @Autowired
    private HubMapper hubMapper;

    @Autowired
    private UserHubMapper userHubMapper;

    @Transactional
    public void register(HubInfoDto hub, UserHubDto role) throws Exception{ //트랜잭션 처리해야
        hubMapper.save(hub);
        role.setHubId(hub.getHubId());

        System.out.println("hub: "+role.getHubId());
        System.out.println("user: "+role.getUserId());

        userHubMapper.save(role);
    }
}
