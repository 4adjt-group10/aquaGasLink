package com.aquagaslink.order_management.queue.dto.client;

import java.util.UUID;

public record OrderToClientOut(UUID clientId, UUID orderId) {
}
