package com.aquagaslink.delivery.model;

import java.math.BigDecimal;

public class DeliveryProduct {

    private String productName;
    private BigDecimal productPrice;

    @Deprecated
    public DeliveryProduct() {
    }

    public DeliveryProduct(String productName, BigDecimal productPrice) {
        this.productName = productName;
        this.productPrice = productPrice;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }
}
