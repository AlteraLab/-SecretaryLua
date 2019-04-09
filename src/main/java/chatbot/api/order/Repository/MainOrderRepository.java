package chatbot.api.order.Repository;

import chatbot.api.order.domain.MainOrder;

public interface MainOrderRepository {

    void save(MainOrder mainOrder);

    MainOrder find(String providerId);
}
