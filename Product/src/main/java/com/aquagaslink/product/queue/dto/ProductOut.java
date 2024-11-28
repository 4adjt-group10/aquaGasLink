package com.aquagaslink.product.queue.dto;

import java.math.BigDecimal;

public record ProductOut(Long id,
                         String name,
                         String description,
                         BigDecimal price,
                         int stock,
                         String productCode)  {
}
