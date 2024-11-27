package com.aquagaslink.client.queue.producer;

import com.aquagaslink.client.queue.ClientEventGateway;
import com.aquagaslink.client.queue.config.QueueProperties;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class DeliveryEventGatewayWithStreamBridge implements ClientEventGateway {

    private final StreamBridge streamBridge;
    private final QueueProperties queueProperties;
    private final Logger logger = Logger.getLogger(DeliveryEventGatewayWithStreamBridge.class.getName());

    public DeliveryEventGatewayWithStreamBridge(StreamBridge streamBridge, QueueProperties queueProperties) {
        this.streamBridge = streamBridge;
        this.queueProperties = queueProperties;
    }

    @Override
    public void sendOrderEvent(String message) {
        streamBridge.send(queueProperties.getAppClientChannel(),message);
        logger.info("Sent to order event: " + message);
    }
}
