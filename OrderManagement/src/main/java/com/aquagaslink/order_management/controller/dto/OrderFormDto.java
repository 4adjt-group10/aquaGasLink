package com.aquagaslink.order_management.controller.dto;

import com.aquagaslink.order_management.model.OrderStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record OrderFormDto(
        String number,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        OrderStatus status,
        String productCode,
        String clientName,
        UUID clientId
) {

}
