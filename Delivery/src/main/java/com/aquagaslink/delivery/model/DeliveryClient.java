package com.aquagaslink.delivery.model;

import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;

public class DeliveryClient {

    private String name;
    private String email;
    private String phone;
    private Address address;
}
