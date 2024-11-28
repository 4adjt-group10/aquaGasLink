package com.aquagaslink.product.queue.consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.logging.Logger;

@Configuration
public class OrderEventListenerConfiguration {
    Logger logger = Logger.getLogger(OrderEventListenerConfiguration.class.getName());

    @Bean
    public Consumer<Message<String>> orderToProductEventListener() {
        return message -> {
            String payload = message.getPayload();
            logger.info("order recebido: " + payload);

            // Processamento da mensagem do produto
            // Confirme a mensagem manualmente se necessário
            MessageHeaders headers = message.getHeaders();
            Long deliveryTag = (Long) headers.get("amqp_deliveryTag");
            Channel channel = (Channel) headers.get("amqp_channel");
            try {
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
