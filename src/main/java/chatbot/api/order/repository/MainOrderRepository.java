package chatbot.api.order.repository;

import chatbot.api.order.domain.MainOrder;

public interface MainOrderRepository {

    void save(MainOrder mainOrder);

    MainOrder find(String providerId);
}
