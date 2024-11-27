package com.aquagaslink.client.queue;

public interface ClientEventGateway {
    void sendOrderEvent(String message);
}
