package com.aquagaslink.order_management.queue.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductToOrderIn(UUID orderId,
                               UUID productId,
                               Integer quantity,
                               BigDecimal price,
                               Boolean hasError,
                               String observation) {
}
