package com.aquagaslink.order_management.queue.producer;

import com.aquagaslink.order_management.queue.OrderEventGateway;
import com.aquagaslink.order_management.queue.config.QueueProperties;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
public class OrderEventGatewayWithStreamBridge implements OrderEventGateway {

    private final  StreamBridge streamBridge;

    private final QueueProperties queueProperties;

    public OrderEventGatewayWithStreamBridge(StreamBridge streamBridge, QueueProperties queueProperties) {
        this.streamBridge = streamBridge;
        this.queueProperties = queueProperties;
    }

    @Override
    public void sendProductEvent(String message) {
        streamBridge.send(queueProperties.getAppProductChannel(),message);
        System.out.println("Sent product event: " + message);
    }

    @Override
    public void sendClientEvent(String message) {
        streamBridge.send(queueProperties.getAppClientChannel(),message);
        System.out.println("Sent product event: " + message);
    }

    @Override
    public void sendDeliveryEvent(String message) {
        streamBridge.send(queueProperties.getAppDeliveryChannel(),message);
        System.out.println("Sent product event: " + message);
    }
}
