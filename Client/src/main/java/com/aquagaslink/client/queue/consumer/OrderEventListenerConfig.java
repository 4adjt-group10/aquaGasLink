package com.aquagaslink.client.queue.consumer;

import com.aquagaslink.client.queue.dto.OrderToClientIn;
import com.aquagaslink.client.service.ClientService;
import com.rabbitmq.client.Channel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.logging.Logger;

@Configuration
public class OrderEventListenerConfig {

    Logger logger = Logger.getLogger(OrderEventListenerConfig.class.getName());

    private final ClientService clientService;

    public OrderEventListenerConfig(ClientService clientService) {
        this.clientService = clientService;
    }

    @Bean
    public Consumer<Message<OrderToClientIn>> orderToClientEventListener() {
        return message -> {
            OrderToClientIn payload = message.getPayload();
            logger.info("Validação de cliente: " + payload);

            // Processamento da mensagem do produto
            // Confirme a mensagem manualmente se necessário
            MessageHeaders headers = message.getHeaders();
            Long deliveryTag = (Long) headers.get("amqp_deliveryTag");
            Channel channel = (Channel) headers.get("amqp_channel");
            try {
                // Processamento da mensagem
                clientService.validateClientToOrder(payload);
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
