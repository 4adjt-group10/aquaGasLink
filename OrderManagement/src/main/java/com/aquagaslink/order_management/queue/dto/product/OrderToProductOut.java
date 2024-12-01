package com.aquagaslink.order_management.queue.dto.product;

import java.util.UUID;

public record OrderToProductOut(UUID productId, Integer quantity, UUID orderId, String clientName) {
}
