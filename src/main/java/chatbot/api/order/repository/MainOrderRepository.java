package chatbot.api.order.repository;

import chatbot.api.order.domain.MainOrder;

public interface MainOrderRepository {

    void save(MainOrder mainOrder);

    void update(MainOrder reMainOrder);

    void delete(String providerId);

    MainOrder find(String providerId);
}
