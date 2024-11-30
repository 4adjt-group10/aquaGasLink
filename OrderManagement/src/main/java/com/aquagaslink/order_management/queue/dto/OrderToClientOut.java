package com.aquagaslink.order_management.queue.dto;

import java.util.UUID;

public record OrderToClientOut(UUID clientId, UUID orderId) {
}
