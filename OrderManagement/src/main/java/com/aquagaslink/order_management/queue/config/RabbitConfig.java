package com.aquagaslink.order_management.queue.config;

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

    public static final String QUEUE_NAME_ORDER_PRODUCT = "OrderProductQueue";
    public static final String ROUTING_KEY_ORDER_PRODUCT = "routing.key.order_product";

    public static final String QUEUE_NAME_ORDER_DELIVERY = "OrderDeliveryQueue";
    public static final String ROUTING_KEY_ORDER_DELIVERY = "routing.key.order_delivery";

    public static final String EXCHANGE_NAME = "OrderExchange";

    @Bean
    public Queue queueProduct() {
        return new Queue(QUEUE_NAME_ORDER_PRODUCT, true); // true indica que a fila é durável
    }

    @Bean
    public Queue queueDelivery() {
        return new Queue(QUEUE_NAME_ORDER_DELIVERY, true);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding bindingProduct(Queue queueProduct, TopicExchange exchange) {
        return BindingBuilder.bind(queueProduct).to(exchange).with(ROUTING_KEY_ORDER_PRODUCT);
    }

    @Bean
    public Binding bindingDelivery(Queue queueDelivery, TopicExchange exchange) {
        return BindingBuilder.bind(queueDelivery).to(exchange).with(ROUTING_KEY_ORDER_DELIVERY);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }
}
