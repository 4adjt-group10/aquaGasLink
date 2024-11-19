package com.aquagaslink.delivery.model;

public class DeliveryClient {

    private String name;
    private String email;
    private String phone;
    private Address address;

    @Deprecated(since = "Only for framework")
    public DeliveryClient() {
    }

    public DeliveryClient(String name, String email, String phone, Address address) {
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

    public Address getAddress() {
        return address;
    }
}
