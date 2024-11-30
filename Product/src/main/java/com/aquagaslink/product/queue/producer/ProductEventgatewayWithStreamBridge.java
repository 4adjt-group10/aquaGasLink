package com.aquagaslink.product.queue.producer;

import com.aquagaslink.product.queue.ProductEventGateway;
import com.aquagaslink.product.queue.config.QueueProperties;
import com.aquagaslink.product.queue.dto.ProductToOrderOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
public class ProductEventgatewayWithStreamBridge implements ProductEventGateway {
    private static final Logger logger = LoggerFactory.getLogger(ProductEventgatewayWithStreamBridge.class);

    private final  StreamBridge streamBridge;

    private final QueueProperties queueProperties;

    public ProductEventgatewayWithStreamBridge(StreamBridge streamBridge, QueueProperties queueProperties) {
        this.streamBridge = streamBridge;
        this.queueProperties = queueProperties;
    }


    @Override
    public void sendOrderEvent(ProductToOrderOut message) {
        streamBridge.send(queueProperties.getAppOrderChannel(),message);
        logger.info("Sent product event: " + message);
    }
}
