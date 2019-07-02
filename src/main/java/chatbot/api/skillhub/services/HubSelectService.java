package chatbot.api.skillhub.services;

import chatbot.api.mappers.HubMapper;
import chatbot.api.skillhub.domain.HubInfoDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class HubSelectService {

    private HubMapper hub;



    // get hubs by adminId
    public List<HubInfoDTO> getHubsInfoByadminId(Long adminId) {

        List<HubInfoDTO> hubsInfoList = null;

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
