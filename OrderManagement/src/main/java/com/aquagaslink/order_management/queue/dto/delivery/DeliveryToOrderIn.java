package com.aquagaslink.order_management.queue.dto.delivery;

import com.aquagaslink.order_management.model.OrderStatus;

import java.util.UUID;

public record DeliveryToOrderIn(UUID orderId, OrderStatus status) {
}
