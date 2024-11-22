package com.aquagaslink.product.queue;

public interface ProductEventGateway {
    void sendProductEvent(String message);
    void sendClientEvent(String message);
}
