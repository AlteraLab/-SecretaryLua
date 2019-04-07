package chatbot.api.order.Repository;

import chatbot.api.order.domain.HubOrderDto;

import java.util.ArrayList;


public interface HubOrderRepository {

    void save(String providerId, HubOrderDto hubOrderDto);

    ArrayList<HubOrderDto> findAll(String providerId);

    void deleteAllByProviderId(String providerId, Long hubId);
}
