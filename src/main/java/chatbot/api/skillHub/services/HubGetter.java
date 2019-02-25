package chatbot.api.skillHub.services;

import chatbot.api.mappers.HubMapper;
import chatbot.api.skillHub.domain.HubTableVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HubGetter {

    @Autowired
    private HubMapper hub;

    // get hubs by adminId
    public List<HubTableVo> getHubsInfoByadminId(Long adminId) {

        List<HubTableVo> hubsInfoList;

        try {
            hubsInfoList = hub.getHubInfoByAdminId(adminId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return hubsInfoList;
    }

}
