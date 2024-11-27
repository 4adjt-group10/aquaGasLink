package com.aquagaslink.delivery.queue.producer;

import com.aquagaslink.delivery.queue.DeliveryEventGateway;
import com.aquagaslink.delivery.queue.config.QueueProperties;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class DeliveryEventGatewayWithStreamBridge implements DeliveryEventGateway {

    private final StreamBridge streamBridge;
    private final QueueProperties queueProperties;
    private final Logger logger = Logger.getLogger(DeliveryEventGatewayWithStreamBridge.class.getName());

    public DeliveryEventGatewayWithStreamBridge(StreamBridge streamBridge, QueueProperties queueProperties) {
        this.streamBridge = streamBridge;
        this.queueProperties = queueProperties;
    }

    @Override
    public void sendOrderEvent(String message) {
        streamBridge.send(queueProperties.getAppOrderChannel(),message);
        logger.info("Sent to order event: " + message);
    }
}
