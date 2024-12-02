package com.aquagaslink.delivery.model;

public enum DeliveryFinishStatus {
    COMPLETED, CANCELLED;

    public DeliveryStatus toDeliveryStatus() {
        return switch (this) {
            case COMPLETED -> DeliveryStatus.DELIVERED;
            case CANCELLED -> DeliveryStatus.CANCELLED;
        };
    }
}
