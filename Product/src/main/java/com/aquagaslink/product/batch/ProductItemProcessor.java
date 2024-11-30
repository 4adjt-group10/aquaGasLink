package com.aquagaslink.product.batch;

import com.aquagaslink.product.infrastructure.ProductRepository;
import com.aquagaslink.product.model.ProductModel;
import io.micrometer.common.util.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
@Component
public class ProductItemProcessor implements ItemProcessor<ProductModel, ProductModel> {


    final ProductRepository productRepository;

    public ProductItemProcessor(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public ProductModel process(ProductModel item) throws Exception {

        Optional<ProductModel> product;

        if(Objects.nonNull(item.getId())){
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
            return  item;
    }
}