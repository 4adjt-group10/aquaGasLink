package com.aquagaslink.product.queue.dto;

import java.util.UUID;

public record OrderToProductIn(UUID productId, Integer quantity, UUID orderId) {
}
