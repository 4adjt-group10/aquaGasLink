package com.aquagaslink.order_management.model;

import com.aquagaslink.order_management.controller.dto.OrderFormDto;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class ClientOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String code;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private String productCode;
    private String clientName;
    private UUID clientId;

    public ClientOrder(String code,
                       LocalDateTime createdAt,
                       LocalDateTime updatedAt,
                       OrderStatus status,
                       String productCode,
                       String clientName,
                       UUID clientId) {
        this.code = code;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        this.productCode = productCode;
        this.clientName = clientName;
        this.clientId = clientId;
    }

    public ClientOrder(OrderFormDto formDto) {
        this(formDto.code(),
                LocalDateTime.now(),
                null,
                formDto.status(),
                formDto.productCode(),
                formDto.clientName(),
                formDto.clientId());
    }

    @Deprecated(since = "Only for frameworks use")
    public ClientOrder() {

    }

    public UUID getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getClientName() {
        return clientName;
    }

    public UUID getClientId() {
        return clientId;
    }

    public void merge(OrderFormDto formDto) {
        this.code = formDto.code();
        this.updatedAt = LocalDateTime.now();
        this.status = formDto.status();
        this.productCode = formDto.productCode();
        this.clientName = formDto.clientName();
        this.clientId = formDto.clientId();
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}