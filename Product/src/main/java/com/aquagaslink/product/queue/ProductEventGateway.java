package com.aquagaslink.product.queue;

import com.aquagaslink.product.queue.dto.ProductToOrderOut;

public interface ProductEventGateway {
    void sendOrderEvent(ProductToOrderOut message);
}
