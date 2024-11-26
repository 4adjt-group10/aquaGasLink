package com.aquagaslink.order_management.queue;

public interface OrderEventGateway {
    void sendProductEvent(String message);
    void sendClientEvent(String message);
    void sendDeliveryEvent(String message);
}
