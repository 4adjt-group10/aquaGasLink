package com.aquagaslink.delivery.queue.dto;

import com.aquagaslink.delivery.model.DeliveryFinishStatus;

import java.util.UUID;

public record DeliveryToOrderOut(UUID orderId, DeliveryFinishStatus status) {

}
