package com.aquagaslink.product.infrastructure;

import com.aquagaslink.product.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductModel, UUID> {
    Optional<ProductModel> findByName(String name);

    Optional<ProductModel> findByProductCode(String productCode);
}
