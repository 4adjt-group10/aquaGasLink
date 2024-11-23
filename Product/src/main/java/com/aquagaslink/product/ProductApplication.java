package com.aquagaslink.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Function;

@SpringBootApplication
public class ProductApplication {

    public static void main(String[] args) {

        SpringApplication.run(ProductApplication.class, args);
    }
    @Bean
    public Function<String, String> productEventListener() {
        return value -> {
            // Lógica do listener de produto
            System.out.println("Product Event: " + value);
            return value;
        };
    }

    @Bean
    public Function<String, String> clientEventListener() {
        return value -> {
            // Lógica do listener de cliente
            System.out.println("Client Event: " + value);
            return value;
        };
    }

    @Bean
    public Function<String, String> orderEventListener() {
        return value -> {
            // Lógica do listener de pedido
            System.out.println("Order Event: " + value);
            return value;
        };
    }
}
