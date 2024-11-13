package com.aquagaslink.product.queue.producer;

import com.aquagaslink.product.queue.config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;


@Service
public class MessageProducer {


    private final RabbitTemplate rabbitTemplate;

    public MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(String message) {
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGENAME, "routing.key.test", message);
    }
}

