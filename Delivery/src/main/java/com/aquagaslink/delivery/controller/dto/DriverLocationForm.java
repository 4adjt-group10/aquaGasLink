package com.aquagaslink.delivery.controller.dto;

import com.aquagaslink.delivery.model.Address;

public record DriverLocationForm(
        String Latitude,
        String Longitude,
        Address address
) {
}
