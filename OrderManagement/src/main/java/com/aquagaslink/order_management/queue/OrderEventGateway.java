package com.aquagaslink.order_management.queue;

import com.aquagaslink.order_management.queue.dto.client.OrderToClientOut;
import com.aquagaslink.order_management.queue.dto.delivery.OrderToDeliveryOut;
import com.aquagaslink.order_management.queue.dto.product.OrderToProductOut;

public interface OrderEventGateway {
    void sendProductEvent(OrderToProductOut message);
    void sendClientEvent(OrderToClientOut message);
    void sendDeliveryEvent(OrderToDeliveryOut message);
}
