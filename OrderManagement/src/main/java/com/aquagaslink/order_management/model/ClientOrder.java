package com.aquagaslink.order_management.model;

import com.aquagaslink.order_management.controller.dto.OrderFormDto;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class ClientOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    private LocalDateTime createdAt;
    @Nullable
    private LocalDateTime updatedAt;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private UUID clientId;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private ClientAddress clientAddress;
    private UUID productId;
    private Integer quantity;
    private BigDecimal price;
    private Boolean hasClientError;
    private Boolean hasProductError;
    private String observation;

    public ClientOrder(LocalDateTime createdAt,
                       LocalDateTime updatedAt,
                       OrderStatus status,
                       UUID clientId,
                       UUID productId,
                       Integer quantity,
                       BigDecimal price) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        this.clientId = clientId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.observation = StringUtils.EMPTY;
    }

    public ClientOrder(OrderFormDto formDto) {
        this(LocalDateTime.now(),
                null,
                OrderStatus.CREATED,
                formDto.clientId(),
                formDto.productId(),
                formDto.quantity(),
                formDto.price());
    }

    @Deprecated(since = "Only for frameworks use")
    public ClientOrder() {

    }

    public UUID getId() {
        return id;
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

    public UUID getClientId() {
        return clientId;
    }

    public boolean isHasClientError() {
        return hasClientError != null && !hasClientError;
    }

    public boolean isHasProductError() {
        return hasProductError != null && !hasProductError;
    }

    public String getObservation() {
        return observation;
    }

    public ClientAddress getClientAddress() {
        return clientAddress;
    }

    public UUID getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void merge(OrderFormDto formDto) {
        this.updatedAt = LocalDateTime.now();
        this.clientId = formDto.clientId();
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setHasClientError(boolean hasClientError) {
        this.hasClientError = hasClientError;
    }

    public void setHasProductError(boolean hasProductError) {
        this.hasProductError = hasProductError;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public void setClientAddress(ClientAddress clientAddress) {
        this.clientAddress = clientAddress;
    }

    public void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }
}
