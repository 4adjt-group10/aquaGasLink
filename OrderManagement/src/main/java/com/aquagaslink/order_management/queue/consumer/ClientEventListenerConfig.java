package com.aquagaslink.order_management.queue.consumer;

import com.aquagaslink.order_management.queue.dto.ClientToOrderIn;
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
public class ClientEventListenerConfig {
    Logger logger = Logger.getLogger(ClientEventListenerConfig.class.getName());

    private final OrderService orderService;

    public ClientEventListenerConfig(OrderService orderService) {
        this.orderService = orderService;
    }

    @Bean
    public Consumer<Message<ClientToOrderIn>> clientToOrderEventListener() {
        return message -> {
            ClientToOrderIn payload = message.getPayload();
            MessageHeaders headers = message.getHeaders();
            Long deliveryTag = (Long) headers.get("amqp_deliveryTag");
            Channel channel = (Channel) headers.get("amqp_channel");
            logger.info("Cliente: " + payload);
            try {
                orderService.validateClient(payload);
                // Processamento da mensagem
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

