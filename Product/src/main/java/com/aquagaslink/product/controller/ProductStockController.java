package com.aquagaslink.product.controller;

import com.aquagaslink.product.controller.dto.ProductCadasterDto;
import com.aquagaslink.product.controller.dto.ProductFormDto;
import com.aquagaslink.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Register new product")
    public ResponseEntity<ProductCadasterDto> registerProduct(@RequestBody ProductFormDto productFormDto) {
        return ResponseEntity.ok(productService.registerProduct(productFormDto));
    }

    @PostMapping("/register-products")
    @Operation(summary = "Register a list of new products")
    public ResponseEntity<List<ProductCadasterDto>> registerProducts(@RequestBody List<ProductFormDto> productFormDto) {
        return ResponseEntity.ok(productService.registerProducts(productFormDto));
    }

    @GetMapping("/find-id/{id}")
    @Operation(summary = "Find product by id")
    public ResponseEntity<ProductCadasterDto> findById(@PathVariable("id") UUID id) {

        return ResponseEntity.ok(productService.findById(id));
    }

    @GetMapping("/find-name/{name}")
    @Operation(summary = "Find product by name")
    public ResponseEntity<ProductCadasterDto> findByName(@PathVariable("name") String name) {
        return ResponseEntity.ok(productService.findByName(name));
    }

    @GetMapping("/find-product/{productcode}")
    @Operation(summary = "Find product by product code")
    public ResponseEntity<ProductCadasterDto> findByProductCode(@PathVariable("productcode") String productcode) {
        return ResponseEntity.ok(productService.findByProductCode(productcode));
    }

    @GetMapping("/find-all")
    @Operation(summary = "List all products")
    public ResponseEntity<List<ProductCadasterDto>> findAll() {

        return ResponseEntity.ok(productService.findAll());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product by id")
    public void deleteProduct(@PathVariable("id") UUID id) {
        productService.deleteById(id);
    }


}
