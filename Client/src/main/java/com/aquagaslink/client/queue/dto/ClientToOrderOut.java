package com.aquagaslink.client.queue.dto;

import com.aquagaslink.client.model.ClientAddress;

import java.util.UUID;

public record ClientToOrderOut(UUID id,
                               UUID orderId,
                               String name,
                               String phone,
                               ClientAddress address,
                               Boolean hasError,
                               String observation) {
}
