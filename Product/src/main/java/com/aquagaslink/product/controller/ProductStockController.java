package com.aquagaslink.product.controller;

import com.aquagaslink.product.controller.dto.ProductCadasterDto;
import com.aquagaslink.product.controller.dto.ProductFormDto;
import com.aquagaslink.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductStockController {
    @Autowired
    ProductService productService;

    @PostMapping("/register")
    public ProductCadasterDto registerProduct(@RequestBody ProductFormDto productFormDto){
        return productService.registerProduct(productFormDto);
    }

    @PostMapping("/register/products")
    public List<ProductCadasterDto> registerProducts(@RequestBody List<ProductFormDto> productFormDto){
        return productService.registerProducts(productFormDto);
    }

    @GetMapping("/find/id/{id}")
    public ProductCadasterDto findById(@PathVariable("id") Long id){
        return productService.findById(id);
    }

    @GetMapping("/find/name/{name}")
    public ProductCadasterDto findByName(@PathVariable("name") String name){
        return productService.findByName(name);
    }

    @GetMapping("/find/product/{productcode}")
    public ProductCadasterDto findByProductCode(@PathVariable("productcode") String productcode){
        return productService.findByProductCode(productcode);
    }

    @GetMapping("/find/all")
    public List<ProductCadasterDto> findAll(){
        return productService.findAll();
    }

}
