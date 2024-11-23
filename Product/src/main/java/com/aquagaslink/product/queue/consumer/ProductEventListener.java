package com.aquagaslink.product.queue.consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ProductEventListener {

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