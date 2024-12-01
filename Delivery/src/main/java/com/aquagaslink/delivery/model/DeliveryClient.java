package com.aquagaslink.delivery.model;

import java.util.UUID;

public class DeliveryClient {

    private UUID clientId;
    private String name;
    private ClientAddress address;

    @Deprecated(since = "Only for framework")
    public DeliveryClient() {
    }

    public DeliveryClient(UUID clientId, String name, ClientAddress address) {
        this.clientId = clientId;
        this.name = name;
        this.address = address;
    }

    public UUID getClientId() {
        return clientId;
    }

    public String getName() {
        return name;
    }

    public ClientAddress getAddress() {
        return address;
    }
}
