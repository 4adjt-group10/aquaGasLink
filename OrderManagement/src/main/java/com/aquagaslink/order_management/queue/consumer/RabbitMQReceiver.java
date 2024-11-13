package com.aquagaslink.order_management.queue.consumer;

import com.aquagaslink.order_management.queue.config.RabbitConfig;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RabbitMQReceiver {

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME_ORDER_PRODUCT, ackMode = "MANUAL")
    public void receiveProductMessage(Message message,
                                      Channel channel,
                                      @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        try {
            SimpleMessageConverter messageConverter = new SimpleMessageConverter();
            String messageBody = (String) messageConverter.fromMessage(message);
            System.out.println("Received <" + messageBody + ">");
            // Adicione aqui a lógica para processar a mensagem
            // Se o processamento for bem-sucedido, confirme a mensagem
            channel.basicAck(deliveryTag , false);
        } catch (Exception e) {
            System.err.println("Error processing message: " + e.getMessage());
            // Se o processamento falhar, rejeite a mensagem sem reencaminhá-la para a fila
            channel.basicNack(deliveryTag, false, false);
            throw new AmqpRejectAndDontRequeueException("Falha no processamento da mensagem", e);
        }
    }

    @RabbitListener(queues = RabbitConfig.ROUTING_KEY_ORDER_DELIVERY, ackMode = "MANUAL")
    public void receiveDeliveryMessage(Message message,
                                      Channel channel,
                                      @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        try {
            SimpleMessageConverter messageConverter = new SimpleMessageConverter();
            String messageBody = (String) messageConverter.fromMessage(message);
            System.out.println("Received <" + messageBody + ">");
            // Adicione aqui a lógica para processar a mensagem
            // Se o processamento for bem-sucedido, confirme a mensagem
            channel.basicAck(deliveryTag , false);
        } catch (Exception e) {
            System.err.println("Error processing message: " + e.getMessage());
            // Se o processamento falhar, rejeite a mensagem sem reencaminhá-la para a fila
            channel.basicNack(deliveryTag, false, false);
            throw new AmqpRejectAndDontRequeueException("Falha no processamento da mensagem", e);
        }
    }
}
