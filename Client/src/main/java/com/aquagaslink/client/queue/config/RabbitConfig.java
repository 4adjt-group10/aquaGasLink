package com.aquagaslink.client.queue.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String QUEUENAME = "ClientQueue";
    public static final String EXCHANGENAME = "ClientExchange";
    public static final String ROUTING_KEY = "routing.key.client";

    @Bean
    public Queue queue() {
        return new Queue(QUEUENAME, true); // true indica que a fila é durável
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGENAME);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }
}
