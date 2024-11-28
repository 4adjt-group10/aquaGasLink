package com.aquagaslink.product.queue;

import com.aquagaslink.product.queue.dto.ProductOut;

public interface ProductEventGateway {
    void sendProductEvent(ProductOut message);
    void sendClientEvent(ProductOut message);
    void sendOrderEvent(ProductOut message);
}
