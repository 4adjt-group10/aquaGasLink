package com.aquagaslink.product.service;

import com.aquagaslink.product.controller.dto.ProductCadasterDto;
import com.aquagaslink.product.controller.dto.ProductFormDto;
import com.aquagaslink.product.infrastructure.ProductRepository;
import com.aquagaslink.product.model.ProductModel;
import io.micrometer.common.util.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;


    public ProductCadasterDto registerProduct (ProductFormDto productFormDto){

        var product  = findByIdOrNameOrProductCode(productFormDto);

        if(Objects.isNull(product)){

            product = createProduct(productFormDto);

        }else{

            product = updateProduct(productFormDto,product);
        }
        return generateDtoOut(product);
    }

    private ProductModel updateProduct(ProductFormDto productFormDto, ProductModel product) {
        product.setPrice(productFormDto.price());
        product.setStock(product.getStock() + productFormDto.stock());
        product = productRepository.save(product);
        return product;
    }

    private ProductModel createProduct(ProductFormDto productFormDto) {
        ProductModel product = new ProductModel(productFormDto.name(),
                productFormDto.description()
                , productFormDto.price(),
                productFormDto.stock(),
                productFormDto.productCode());
        product = productRepository.save(product);
        return product;
    }

    private ProductModel findByIdOrNameOrProductCode(ProductFormDto productFormDto) {

        Optional<ProductModel> product;
        if(Objects.nonNull(productFormDto.id()) && productFormDto.id() > 0){
            product = productRepository.findById(productFormDto.id());
        }else if(StringUtils.isNotEmpty(productFormDto.name())) {
            product = productRepository.findByName(productFormDto.name());
        }else {
            product = productRepository.findByProductCode(productFormDto.productCode());
        }
        return product.orElse(null);
    }

    public List<ProductCadasterDto> registerProducts(List<ProductFormDto> productFormDto) {
        List<ProductCadasterDto> products = new ArrayList<>();
        productFormDto.forEach(product ->
                products.add(registerProduct(product)));
        return products;
    }

    public ProductCadasterDto findById(Long id) {
        var product = productRepository.findById(id);
        return product.map(ProductService::generateDtoOut).orElse(null);
    }

    public ProductCadasterDto findByName(String name) {
        var product = productRepository.findByName(name);
        return product.map(ProductService::generateDtoOut).orElse(null);
    }

    public ProductCadasterDto findByProductCode(String productCode) {
        var product = productRepository.findByProductCode(productCode);
        return product.map(ProductService::generateDtoOut).orElse(null);
    }

    private static @NotNull ProductCadasterDto generateDtoOut(ProductModel productModel) {
        return new ProductCadasterDto(productModel.getId(),
                productModel.getName(),
                productModel.getDescription(),
                productModel.getPrice(),
                productModel.getStock());
    }

    public List<ProductCadasterDto> findAll() {
        List<ProductCadasterDto> productCadasterDtos = new ArrayList<>();
        var produtos = productRepository.findAll();
        produtos.forEach(p -> productCadasterDtos.add(generateDtoOut(p)));
        return productCadasterDtos;
    }
}
