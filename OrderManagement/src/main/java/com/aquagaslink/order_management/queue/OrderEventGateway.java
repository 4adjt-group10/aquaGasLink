package com.aquagaslink.order_management.queue;

import com.aquagaslink.order_management.queue.dto.OrderToClientOut;
import com.aquagaslink.order_management.queue.dto.OrderToProductOut;

public interface OrderEventGateway {
    void sendProductEvent(OrderToProductOut message);
    void sendClientEvent(OrderToClientOut message);
    void sendDeliveryEvent(String message);
}
