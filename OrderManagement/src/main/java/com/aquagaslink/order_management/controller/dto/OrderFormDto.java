package com.aquagaslink.order_management.controller.dto;

import com.aquagaslink.order_management.model.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record OrderFormDto(
        @NotBlank String code,
        @NotNull OrderStatus status,
        @NotBlank String productCode,
        @NotBlank String clientName,
        @NotNull UUID clientId
) {

}
