package com.aquagaslink.product.queue;

import com.aquagaslink.product.queue.dto.ProductToOrderOut;

public interface ProductEventGateway {
    void sendProductEvent(ProductOut message);
    void sendClientEvent(ProductOut message);
    void sendOrderEvent(ProductToOrderOut message);
}
