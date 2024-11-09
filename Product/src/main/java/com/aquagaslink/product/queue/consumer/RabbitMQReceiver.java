package com.aquagaslink.product.queue.consumer;

import com.aquagaslink.product.queue.config.RabbitConfig;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import com.rabbitmq.client.Channel;

import java.io.IOException;

@Component
public class RabbitMQReceiver {

    @RabbitListener(queues = RabbitConfig.QUEUENAME, ackMode = "MANUAL")
    public void receiveMessage(Message message,
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
