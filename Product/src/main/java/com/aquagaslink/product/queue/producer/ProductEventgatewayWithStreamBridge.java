package com.aquagaslink.product.queue.producer;

import com.aquagaslink.product.queue.ProductEventGateway;
import com.aquagaslink.product.queue.config.ProductProperties;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
public class ProductEventgatewayWithStreamBridge implements ProductEventGateway {

    private final  StreamBridge streamBridge;

    private final ProductProperties productProperties;

    public ProductEventgatewayWithStreamBridge(StreamBridge streamBridge, ProductProperties productProperties) {
        this.streamBridge = streamBridge;
        this.productProperties = productProperties;
    }


    @Override
    public void sendProductEvent(String message) {
        streamBridge.send(productProperties.getAppOrderChannel(),message);
        System.out.println("Sent to order event: " + message);
    }

    @Override
    public void sendClientEvent(String message) {
//        streamBridge.send(productProperties.getAppClientChannel(),message);
//        System.out.println("Sent product event: " + message);
    }

    @Override
    public void sendOrderEvent(String message) {
//        streamBridge.send(productProperties.getAppOrderChannel(),message);
//        System.out.println("Sent product event: " + message);
    }
}
