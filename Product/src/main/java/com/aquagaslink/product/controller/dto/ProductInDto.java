package com.aquagaslink.product.controller.dto;

import java.util.UUID;

public record ProductInDto (
        Long id,
        String name,
        String description,
        double price,
        int stock
){

}
