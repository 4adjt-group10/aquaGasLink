package com.aquagaslink.delivery.controller.dto;

import com.aquagaslink.delivery.model.ClientAddress;

public record DriverLocationForm(
        String Latitude,
        String Longitude,
        ClientAddress address
) {
}
