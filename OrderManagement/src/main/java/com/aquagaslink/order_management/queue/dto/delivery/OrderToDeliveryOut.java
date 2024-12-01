package com.aquagaslink.order_management.queue.dto.delivery;

import com.aquagaslink.order_management.model.ClientAddress;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderToDeliveryOut(
        UUID orderId,
        UUID clientId,
        String clientName,
        ClientAddress address,
        String productName,
        BigDecimal productPrice
) {

}
