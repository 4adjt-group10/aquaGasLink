package com.aquagaslink.delivery.model;

import com.aquagaslink.delivery.controller.dto.DeliveryPersonForm;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class DeliveryPerson {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    private String name;
    private String email;
    private String phone;
    private DeliveryPersonStatus status;
    private String vehiclePlate;

    @Deprecated(since = "Only for framework")
    public DeliveryPerson() {
    }

    public DeliveryPerson(String name, String email, String phone, DeliveryPersonStatus status, String vehiclePlate) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.status = status;
        this.vehiclePlate = vehiclePlate;
    }

    public DeliveryPerson(DeliveryPersonForm deliveryPersonForm) {
        this.name = deliveryPersonForm.name();
        this.email = deliveryPersonForm.email();
        this.phone = deliveryPersonForm.phone();
    }

    public UUID getId() {
        return id;
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

    public DeliveryPersonStatus getStatus() {
        return status;
    }

    public String getVehiclePlate() {
        return vehiclePlate;
    }

    public void merge(DeliveryPersonForm form) {
        this.name = form.name();
        this.email = form.email();
        this.phone = form.phone();
    }
}
