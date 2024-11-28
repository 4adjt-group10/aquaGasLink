package com.aquagaslink.product.controller;

import com.aquagaslink.product.controller.dto.ProductCadasterDto;
import com.aquagaslink.product.controller.dto.ProductFormDto;
import com.aquagaslink.product.queue.ProductEventGateway;
import com.aquagaslink.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.EventListener;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductStockController {


    final ProductService productService;
    final ProductEventGateway productEventGateway;


    public ProductStockController(ProductService productService, ProductEventGateway productEventGateway) {
        this.productService = productService;
        this.productEventGateway = productEventGateway;
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
    public ResponseEntity<ProductCadasterDto> findById(@PathVariable("id") Long id) {

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
    public void deleteProduct(@PathVariable("id") Long id) {
        productService.deleteById(id);
    }

//    @GetMapping("teste")
//    public void teste() {
//
//            productInput.send(MessageBuilder.withPayload().build());
//        }
//    }
//
//    @PostMapping("/send")
//    public String sendMessageToProductQueue(@RequestParam String message) {
//        for (int i = 0; i < 10; i++) {
//            productEventGateway.sendProductEvent("Message teste " + i + " numero randomico :" + Math.random());
//        }
//
//        return "Mensagem enviada para a fila de produtos: " + message;
//    }

}
