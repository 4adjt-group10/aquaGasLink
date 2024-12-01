package com.aquagaslink.order_management.queue.dto.product;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductToOrderIn(UUID orderId,
                               UUID productId,
                               String clientName,
                               String productName,
                               Integer quantity,
                               BigDecimal price,
                               Boolean hasError,
                               String observation) {
}
