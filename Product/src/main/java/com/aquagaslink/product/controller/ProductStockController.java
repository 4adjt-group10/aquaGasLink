package com.aquagaslink.product.controller;

import com.aquagaslink.product.controller.dto.ProductInDto;
import com.aquagaslink.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductStockController {
    @Autowired
    ProductService productService;

    @PostMapping("/register")
    public void registerProduct(@RequestBody ProductInDto productInDto){
        var register = productService.registerProduct(productInDto);
    }
}
