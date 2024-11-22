package com.aquagaslink.delivery.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DeliveryPersonForm(@NotBlank String name, @Email String email, @NotBlank String phone) {
}
