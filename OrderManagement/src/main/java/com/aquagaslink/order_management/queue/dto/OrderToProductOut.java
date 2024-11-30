package com.aquagaslink.order_management.queue.dto;

import java.util.UUID;

public record OrderToProductOut(UUID productId, Integer quantity, UUID orderId) {
}
