package com.aquagaslink.product.model;

import com.aquagaslink.product.controller.dto.ProductFormDto;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class ProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String productCode;
    private String name;
    private String description;
    private BigDecimal price;
    private int stock;

    public ProductModel() {
    }

    public ProductModel(Long id, String name, String description, BigDecimal price, int stock, String productCode) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.productCode = productCode;
    }

    public ProductModel(Long id, String description, String name, BigDecimal price, String productCode, int stock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.productCode = productCode;
    }

    public ProductModel(ProductFormDto productFormDto) {
        this.name = productFormDto.name();
        this.description = productFormDto.description();
        this.price = productFormDto.price();
        this.stock = productFormDto.stock();
        this.productCode = productFormDto.productCode();
    }

    public ProductModel(String name, String description, BigDecimal price, int stock, String productCode) {
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

    public BigDecimal getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
