package com.aquagaslink.product.queue.producer;

import com.aquagaslink.product.queue.ProductEventGateway;
import com.aquagaslink.product.queue.config.QueueProperties;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
public class ProductEventgatewayWithStreamBridge implements ProductEventGateway {

    private final  StreamBridge streamBridge;

    private final QueueProperties queueProperties;

    public ProductEventgatewayWithStreamBridge(StreamBridge streamBridge, QueueProperties queueProperties) {
        this.streamBridge = streamBridge;
        this.queueProperties = queueProperties;
    }


    @Override
    public void sendProductEvent(String message) {
        streamBridge.send(queueProperties.getAppOrderChannel(),message);
        System.out.println("Sent to order event: " + message);
    }

    @Override
    public void sendClientEvent(String message) {
        streamBridge.send(queueProperties.getAppClientChannel(),message);
        System.out.println("Sent product event: " + message);
    }

    @Override
    public void sendOrderEvent(String message) {
        streamBridge.send(queueProperties.getAppOrderChannel(),message);
        System.out.println("Sent product event: " + message);
    }
}
