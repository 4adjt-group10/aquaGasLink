package com.aquagaslink.product.service;

import com.aquagaslink.product.controller.dto.ProductCadasterOutDto;
import com.aquagaslink.product.controller.dto.ProductInDto;
import com.aquagaslink.product.infrastructure.ProductRepository;
import com.aquagaslink.product.model.ProductModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;


    public ProductCadasterOutDto registerProduct (ProductInDto productInDto){
        var product  = findByIdOrName(productInDto);
        if(Objects.isNull(product)){
            product = createProduct(productInDto);
        }else{
            product = updateProduct(productInDto,product);
        }
        return new ProductCadasterOutDto(product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock());
    }

    private ProductModel updateProduct(ProductInDto productInDto, ProductModel product) {
        product.setPrice(productInDto.price());
        product.setStock(product.getStock() + productInDto.stock());
        product = productRepository.save(product);
        return product;
    }

    private ProductModel createProduct(ProductInDto productInDto) {
        ProductModel product = new ProductModel(productInDto.name(),
                productInDto.description()
                , productInDto.price(),
                productInDto.stock());
        product = productRepository.save(product);
        return product;
    }

    private ProductModel findByIdOrName(ProductInDto productInDto) {
        ProductModel productModel = new ProductModel();
        Optional<ProductModel> product;
        if(Objects.nonNull(productInDto.id())){
            product = productRepository.findById(productInDto.id());
        }else {
            product = productRepository.findByName(productInDto.name());
        }
        if(product.isPresent()){
            productModel = product.get();
            return productModel;
        }
        return productModel;
    }
}
