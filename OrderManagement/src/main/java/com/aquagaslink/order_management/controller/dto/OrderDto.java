package com.aquagaslink.order_management.controller.dto;

import com.aquagaslink.order_management.model.ClientOrder;
import com.aquagaslink.order_management.model.OrderStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record OrderDto(
        UUID id,
        String number,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        OrderStatus status,
        String productCode,
        String clientName,
        UUID clientId
) {

    public OrderDto(ClientOrder clientOrder) {
        this(clientOrder.getId(),
                clientOrder.getNumber(),
                clientOrder.getCreatedAt(),
                clientOrder.getUpdatedAt(),
                clientOrder.getStatus(),
                clientOrder.getProductCode(),
                clientOrder.getClientName(),
                clientOrder.getClientId());
    }
}
