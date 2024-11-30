package com.aquagaslink.product.service;

import com.aquagaslink.product.controller.dto.ProductCadasterDto;
import com.aquagaslink.product.controller.dto.ProductFormDto;
import com.aquagaslink.product.infrastructure.ProductRepository;
import com.aquagaslink.product.model.ProductModel;
import com.aquagaslink.product.queue.ProductEventGateway;
import com.aquagaslink.product.queue.dto.OrderToProductIn;
import com.aquagaslink.product.queue.dto.ProductToOrderOut;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;

@Service
public class ProductService {

    Logger logger = Logger.getLogger(ProductService.class.getName());

    private static final String PRODUCT_NOT_FOUND = "Product not found";
    final ProductRepository productRepository;
    private final ProductEventGateway productEventGateway;

    public ProductService(ProductRepository productRepository, ProductEventGateway productEventGateway) {
        this.productRepository = productRepository;
        this.productEventGateway = productEventGateway;
    }


    public ProductCadasterDto registerProduct(ProductFormDto productFormDto) {

        var product = findByIdOrNameOrProductCode(productFormDto);

        if (Objects.isNull(product)) {

            product = createProduct(productFormDto);

        } else {

            product = updateProduct(productFormDto, product);
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
        ProductModel product = new ProductModel(productFormDto.name(), productFormDto.description(), productFormDto.price(), productFormDto.stock(), productFormDto.productCode());
        product = productRepository.save(product);
        return product;
    }

    private ProductModel findByIdOrNameOrProductCode(ProductFormDto productFormDto) {

        Optional<ProductModel> product;
        if (Objects.nonNull(productFormDto.id())) {
            product = productRepository.findById(productFormDto.id());
        } else if (StringUtils.isNotEmpty(productFormDto.name())) {
            product = productRepository.findByName(productFormDto.name());
        } else {
            product = productRepository.findByProductCode(productFormDto.productCode());
        }
        return product.orElse(null);
    }

    public List<ProductCadasterDto> registerProducts(List<ProductFormDto> productFormDto) {
        List<ProductCadasterDto> products = new ArrayList<>();
        productFormDto.forEach(product -> products.add(registerProduct(product)));
        return products;
    }

    public ProductCadasterDto findById(UUID id) {
        var product = productRepository.findById(id);
        return product.map(ProductService::generateDtoOut).orElseThrow(() -> new EntityNotFoundException(PRODUCT_NOT_FOUND));
    }

    public ProductCadasterDto findByName(String name) {
        var product = productRepository.findByName(name);
        return product.map(ProductService::generateDtoOut).orElseThrow(() -> new EntityNotFoundException(PRODUCT_NOT_FOUND));
    }

    public ProductCadasterDto findByProductCode(String productCode) {
        var product = productRepository.findByProductCode(productCode);
        return product.map(ProductService::generateDtoOut).orElseThrow(() -> new EntityNotFoundException(PRODUCT_NOT_FOUND));
    }

    private static @NotNull ProductCadasterDto generateDtoOut(ProductModel productModel) {
        return new ProductCadasterDto(productModel.getId(), productModel.getName(), productModel.getDescription(), productModel.getPrice(), productModel.getStock(), productModel.getProductCode());
    }

    public List<ProductCadasterDto> findAll() {
        List<ProductCadasterDto> productCadasterDtos = new ArrayList<>();
        var produtos = productRepository.findAll();
        produtos.forEach(p -> productCadasterDtos.add(generateDtoOut(p)));
        return productCadasterDtos;
    }

    public void deleteById(UUID id) {
        productRepository.deleteById(id);
    }

    public void validateProduct(OrderToProductIn payload) {
        Optional<ProductModel> productModel = productRepository.findById(payload.productId());
        ProductToOrderOut productToOrderOut;
        if (productModel.isPresent()) {
            var product = productModel.get();
            if (product.getStock() < payload.quantity() || product.getStock() < 1) {
                productToOrderOut = new ProductToOrderOut(
                        payload.orderId(),
                        product.getId(),
                        payload.quantity(),
                        product.getPrice(),
                        true,
                        "Estoque abaixo do pedido"
                );
                logger.severe("Product with low stock: " + payload.productId());

            } else {
                productToOrderOut = new ProductToOrderOut(
                        payload.orderId(),
                        product.getId(),
                        payload.quantity(),
                        product.getPrice(),
                        false,
                        ""
                );
                product.setStock(product.getStock() - payload.quantity());
                productRepository.save(product);
                logger.info("Product found: " + product.getName());
            }


        } else {
            productToOrderOut = new ProductToOrderOut(payload.orderId(),
                    payload.productId(),
                    payload.quantity(),
                    null,
                    true,
                    "Product not found"
            );
            logger.severe("Product not found: " + payload.productId());
        }

        productEventGateway.sendOrderEvent(productToOrderOut);
    }
}
