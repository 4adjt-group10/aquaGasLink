package com.aquagaslink.order_management.queue.dto.client;

import com.aquagaslink.order_management.model.ClientAddress;

import java.util.UUID;

public record ClientToOrderIn(UUID id,
                              UUID orderId,
                              String name,
                              String phone,
                              ClientAddress address,
                              Boolean hasError,
                              String observation) {
}
