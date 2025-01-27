package com.aquagaslink.order_management.queue.consumer;

import com.aquagaslink.order_management.queue.dto.delivery.DeliveryToOrderIn;
import com.aquagaslink.order_management.service.OrderService;
import com.rabbitmq.client.Channel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.logging.Logger;

@Configuration
public class DeliveryEventListenerConfig {
    Logger logger = Logger.getLogger(DeliveryEventListenerConfig.class.getName());

    private final OrderService orderService;

    public DeliveryEventListenerConfig(OrderService orderService) {
        this.orderService = orderService;
    }

    @Bean
    public Consumer<Message<DeliveryToOrderIn>> deliveryToOrderEventListener() {
        return message -> {
            DeliveryToOrderIn payload = message.getPayload();
            logger.info("delivery: " + payload);

            // Processamento da mensagem do produto
            // Confirme a mensagem manualmente se necessário
            MessageHeaders headers = message.getHeaders();
            Long deliveryTag = (Long) headers.get("amqp_deliveryTag");
            Channel channel = (Channel) headers.get("amqp_channel");
            try {
                // Processamento da mensagem
                orderService.updateOrderStatus(payload.orderId(), payload.status());
                channel.basicAck(deliveryTag, false);
            } catch (Exception e) {
                // Em caso de erro, nack a mensagem para DLQ
                try {
                    channel.basicNack(deliveryTag, false, false);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        };
    }
}

