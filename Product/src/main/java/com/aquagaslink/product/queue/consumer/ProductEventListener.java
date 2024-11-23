package com.aquagaslink.product.queue.consumer;

import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class ProductEventListener implements Consumer<String> {
    @Override
    public void accept(String s) {
        System.out.println("product:" + s);
    }
}
