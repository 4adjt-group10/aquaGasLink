package com.aquagaslink.delivery.queue;

import com.aquagaslink.delivery.queue.dto.DeliveryToOrderOut;

public interface DeliveryEventGateway {
    void sendOrderEvent(DeliveryToOrderOut message);
}
