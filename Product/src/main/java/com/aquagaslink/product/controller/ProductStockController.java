package com.aquagaslink.product.controller;

import com.aquagaslink.product.controller.dto.ProductCadasterDto;
import com.aquagaslink.product.controller.dto.ProductFormDto;
import com.aquagaslink.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductStockController {


    final ProductService productService;



    public ProductStockController(ProductService productService) {
        this.productService = productService;

    }

    @PostMapping("/register")
    public ResponseEntity<ProductCadasterDto> registerProduct(@RequestBody ProductFormDto productFormDto) {
        return ResponseEntity.ok(productService.registerProduct(productFormDto));
    }

    @PostMapping("/register-products")
    public ResponseEntity<List<ProductCadasterDto>> registerProducts(@RequestBody List<ProductFormDto> productFormDto) {
        return ResponseEntity.ok(productService.registerProducts(productFormDto));
    }

    @GetMapping("/find-id/{id}")
    public ResponseEntity<ProductCadasterDto> findById(@PathVariable("id") UUID id) {

        return ResponseEntity.ok(productService.findById(id));
    }

    @GetMapping("/find-name/{name}")
    public ResponseEntity<ProductCadasterDto> findByName(@PathVariable("name") String name) {
        return ResponseEntity.ok(productService.findByName(name));
    }

    @GetMapping("/find-product/{productcode}")
    public ResponseEntity<ProductCadasterDto> findByProductCode(@PathVariable("productcode") String productcode) {
        return ResponseEntity.ok(productService.findByProductCode(productcode));
    }

    @GetMapping("/find-all")
    public ResponseEntity<List<ProductCadasterDto>> findAll() {

        return ResponseEntity.ok(productService.findAll());
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") UUID id) {
        productService.deleteById(id);
    }


}
