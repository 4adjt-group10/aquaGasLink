package com.aquagaslink.product.controller.dto;

public record ProductCadasterDto(Long id,
                                 String name,
                                 String description,
                                 double price,
                                 int stock) {
}
