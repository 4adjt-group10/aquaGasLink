package com.aquagaslink.product.model;

import jakarta.persistence.*;

@Entity
public class ProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String productCode;
    private String name;
    private String description;
    private double price;
    private int stock;

    public ProductModel() {
    }

    public ProductModel(Long id, String name, String description, double price, int stock, String productCode) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.productCode = productCode;
    }

    public ProductModel(String name, String description, double price, int stock, String productCode) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.productCode = productCode;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getProductCode() {
        return productCode;
    }
}
