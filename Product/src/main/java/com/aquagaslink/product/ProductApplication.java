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
        return value -> value;
    }

    @Bean
    public Function<String, String> clientEventListener() {
        return value -> value;
    }
}
