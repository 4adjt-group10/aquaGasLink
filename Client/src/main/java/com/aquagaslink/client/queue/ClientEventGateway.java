package com.aquagaslink.client.queue;

import com.aquagaslink.client.queue.dto.ClientToOrderOut;

public interface ClientEventGateway {
    void sendOrderEvent(ClientToOrderOut message);
}
