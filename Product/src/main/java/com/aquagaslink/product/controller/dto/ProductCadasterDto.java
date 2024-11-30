package com.aquagaslink.product.controller.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductCadasterDto(UUID id,
                                 String name,
                                 String description,
                                 BigDecimal price,
                                 int stock,
                                 String productCode) {
}
