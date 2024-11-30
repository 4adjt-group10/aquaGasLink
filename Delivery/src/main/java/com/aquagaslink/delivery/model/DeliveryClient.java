package com.aquagaslink.delivery.model;

public class DeliveryClient {

    private String name;
    private String email;
    private String phone;
    private ClientAddress address;

    @Deprecated(since = "Only for framework")
    public DeliveryClient() {
    }

    public DeliveryClient(String name, String email, String phone, ClientAddress address) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public ClientAddress getAddress() {
        return address;
    }
}
