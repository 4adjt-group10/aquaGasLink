package com.aquagaslink.product.queue.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductToOrderOut(UUID orderId,
                                UUID productId,
                                String productName,
                                Integer quantity,
                                BigDecimal price,
                                Boolean hasError,
                                String observation) {
}
