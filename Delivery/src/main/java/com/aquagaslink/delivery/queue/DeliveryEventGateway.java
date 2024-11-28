package com.aquagaslink.delivery.queue;

public interface DeliveryEventGateway {
    void sendOrderEvent(String message);
}
