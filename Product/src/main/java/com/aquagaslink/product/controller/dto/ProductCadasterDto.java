package com.aquagaslink.product.controller.dto;

import java.math.BigDecimal;

public record ProductCadasterDto(Long id,
                                 String name,
                                 String description,
                                 BigDecimal price,
                                 int stock) {
}
