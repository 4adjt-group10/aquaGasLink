package com.aquagaslink.client.queue.producer;

import com.aquagaslink.client.queue.ClientEventGateway;
import com.aquagaslink.client.queue.config.QueueProperties;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class ClientEventGatewayWithStreamBridge implements ClientEventGateway {

    private final StreamBridge streamBridge;
    private final QueueProperties queueProperties;
    private final Logger logger = Logger.getLogger(ClientEventGatewayWithStreamBridge.class.getName());

    public ClientEventGatewayWithStreamBridge(StreamBridge streamBridge, QueueProperties queueProperties) {
        this.streamBridge = streamBridge;
        this.queueProperties = queueProperties;
    }

    @Override
    public void sendOrderEvent(String message) {
        streamBridge.send(queueProperties.getAppClientChannel(),message);
        logger.info("Sent to order event: " + message);
    }
}
