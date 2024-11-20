package com.aquagaslink.delivery.controller.dto;

import com.aquagaslink.delivery.model.DeliveryPerson;
import com.aquagaslink.delivery.model.DeliveryPersonStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record DeliveryPersonDto(UUID id, String name, String email, String phone, String vehiclePlate, DeliveryPersonStatus status) {

    public DeliveryPersonDto(DeliveryPerson deliveryPerson) {
        this(deliveryPerson.getId(),
                deliveryPerson.getName(),
                deliveryPerson.getEmail(),
                deliveryPerson.getPhone(),
                deliveryPerson.getVehiclePlate(),
                deliveryPerson.getStatus());
    }
}
