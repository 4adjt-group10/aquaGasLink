package com.aquagaslink.delivery.model;

import java.util.List;
import java.util.UUID;

public class Delivery {

    private DeliveryPerson deliveryPerson;
    private UUID orderId;
    private List<UUID> productIds;
    private DeliveryClient deliveryClient;
    private String latitude;
    private String longitude;
    private DeliveryStatus status;
    private String observation;
}
