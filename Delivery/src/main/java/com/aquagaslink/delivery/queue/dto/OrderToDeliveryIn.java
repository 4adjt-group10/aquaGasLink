package com.aquagaslink.delivery.queue.dto;

import com.aquagaslink.delivery.model.ClientAddress;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderToDeliveryIn(
        UUID orderId,
        UUID clientId,
        ClientAddress address,
        String productName,
        BigDecimal productPrice
) {

}
