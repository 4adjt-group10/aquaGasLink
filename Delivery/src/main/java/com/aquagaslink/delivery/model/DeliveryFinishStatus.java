package com.aquagaslink.delivery.model;

public enum DeliveryFinishStatus {
    DELIVERED, CANCELLED;

    public DeliveryStatus toDeliveryStatus() {
        return switch (this) {
            case DELIVERED -> DeliveryStatus.DELIVERED;
            case CANCELLED -> DeliveryStatus.CANCELLED;
        };
    }
}
