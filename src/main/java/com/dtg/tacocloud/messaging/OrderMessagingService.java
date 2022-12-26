package com.dtg.tacocloud.messaging;

import com.dtg.tacocloud.model.TacoOrder;

public interface OrderMessagingService {

    void sendOrder(TacoOrder order);
}
