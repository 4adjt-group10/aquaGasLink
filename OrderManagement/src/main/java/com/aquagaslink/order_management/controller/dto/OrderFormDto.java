package com.aquagaslink.order_management.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderFormDto(
        @NotNull UUID productId,
        @NotNull @Min(1) Integer quantity,
        @NotNull @Min(1) BigDecimal price,
        @NotNull UUID clientId

) {

}
