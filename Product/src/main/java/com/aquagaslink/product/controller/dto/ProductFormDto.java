package com.aquagaslink.product.controller.dto;

public record ProductFormDto(
        Long id,
        String name,
        String description,
        double price,
        int stock,
        String productCode
){

}
