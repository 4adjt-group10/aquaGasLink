package com.aquagaslink.product.batch;

import com.aquagaslink.product.infrastructure.ProductRepository;
import com.aquagaslink.product.model.ProductModel;
import io.micrometer.common.util.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;
import java.util.Optional;

public class ProductItemProcessor implements ItemProcessor<ProductModel, ProductModel> {
    @Autowired
    ProductRepository productRepository;


    @Override
    public ProductModel process(ProductModel item) throws Exception {
        Optional<ProductModel> product;
        if(Objects.nonNull(item.getId()) && item.getId() > 0){
            product = productRepository.findById(item.getId());
        }else if(StringUtils.isNotEmpty(item.getName())) {
            product = productRepository.findByName(item.getName());
        }else {
            product = productRepository.findByProductCode(item.getProductCode());
        }
        if(product.isPresent()){
            product.get().setPrice(item.getPrice());
            product.get().setStock(product.get().getStock() + item.getStock());
            return product.get();
        }else
            return item;
    }
}