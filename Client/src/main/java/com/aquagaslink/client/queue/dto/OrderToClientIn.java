package com.aquagaslink.client.queue.dto;

import java.util.UUID;

public record OrderToClientIn(UUID clientId, UUID orderId) {
}
