package chatbot.api.skillHub.services;

import chatbot.api.mappers.HubMapper;
import chatbot.api.skillHub.domain.HubInfoDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class HubSelectService {

    private HubMapper hub;



    // get hubs by adminId
    public List<HubInfoDto> getHubsInfoByadminId(Long adminId) {

        List<HubInfoDto> hubsInfoList = null;

        try {
            hubsInfoList = hub.getHubInfoByAdminId(adminId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            return hubsInfoList;
        }
    }
}
