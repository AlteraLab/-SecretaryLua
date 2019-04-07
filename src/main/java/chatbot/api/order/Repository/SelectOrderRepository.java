package chatbot.api.order.Repository;

import chatbot.api.order.domain.SelectOrderVo;

public interface SelectOrderRepository {


    void save(SelectOrderVo selectOrderVo);

    SelectOrderVo find(String providerId);
}
