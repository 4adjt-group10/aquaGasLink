package com.aquagaslink.order_management.queue.consumer;

import com.aquagaslink.order_management.queue.dto.ClientToOrderIn;
import com.rabbitmq.client.Channel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.io.IOException;
import java.util.function.Consumer;

@Configuration
public class ClientEventListenerConfig {

    @Bean
    public Consumer<Message<ClientToOrderIn>> clientToOrderEventListener() {
        return message -> {
            ClientToOrderIn payload = message.getPayload();
            System.out.println("Cliente: " + payload);

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
