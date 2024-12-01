package com.aquagaslink.delivery.controller.dto;

import com.aquagaslink.delivery.model.DeliveryPersonStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DeliveryPersonForm(
        @NotBlank String name,
        @Email String email,
        @NotBlank String phone,
        DeliveryPersonStatus status,
        @NotBlank String vehiclePlate
) {
}
