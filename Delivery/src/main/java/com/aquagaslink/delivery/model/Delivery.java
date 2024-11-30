package com.aquagaslink.delivery.model;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.UUID;

@Entity
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    private DeliveryPerson deliveryPerson;
    private UUID orderId;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<UUID> productIds;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private DeliveryClient deliveryClient;
    private String latitude;
    private String longitude;
    private DeliveryStatus status;
    private String observation;

    @Deprecated(since = "Only for framework")
    public Delivery() {
    }

    public Delivery(UUID id,
                    DeliveryPerson deliveryPerson,
                    UUID orderId,
                    List<UUID> productIds,
                    DeliveryClient deliveryClient,
                    String latitude,
                    String longitude,
                    DeliveryStatus status,
                    String observation) {
        this.id = id;
        this.deliveryPerson = deliveryPerson;
        this.orderId = orderId;
        this.productIds = productIds;
        this.deliveryClient = deliveryClient;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
        this.observation = observation;
    }

    public Delivery(DeliveryPerson deliveryPerson,
                    UUID orderId,
                    List<UUID> productIds,
                    DeliveryClient deliveryClient,
                    String latitude,
                    String longitude,
                    DeliveryStatus status,
                    String observation) {
        this.deliveryPerson = deliveryPerson;
        this.orderId = orderId;
        this.productIds = productIds;
        this.deliveryClient = deliveryClient;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
        this.observation = observation;
    }

    public Delivery(UUID orderId, DeliveryClient deliveryClient, DeliveryStatus status) {
        this.orderId = orderId;
        this.deliveryClient = deliveryClient;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public DeliveryPerson getDeliveryPerson() {
        return deliveryPerson;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public List<UUID> getProductIds() {
        return productIds;
    }

    public DeliveryClient getDeliveryClient() {
        return deliveryClient;
    }


    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public DeliveryStatus getStatus() {
        return status;
    }

    public String getObservation() {
        return observation;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setStatus(DeliveryStatus status) {
        this.status = status;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }
}
