package com.aquagaslink.order_management.controller.dto;

import com.aquagaslink.order_management.model.ClientOrder;
import com.aquagaslink.order_management.model.OrderStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record OrderDto(
        UUID id,
        String code,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        OrderStatus status,
        String productCode,
        String clientName,
        UUID clientId
) {

    public OrderDto(ClientOrder clientOrder) {
        this(clientOrder.getId(),
                clientOrder.getCode(),
                clientOrder.getCreatedAt(),
                clientOrder.getUpdatedAt(),
                clientOrder.getStatus(),
                clientOrder.getProductCode(),
                clientOrder.getClientName(),
                clientOrder.getClientId());
    }
}