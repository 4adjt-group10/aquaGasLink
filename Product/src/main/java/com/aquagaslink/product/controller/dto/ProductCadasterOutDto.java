package com.aquagaslink.product.controller.dto;

public record ProductCadasterOutDto(Long id,
                                    String name,
                                    String description,
                                    double price,
                                    int stock) {
}
