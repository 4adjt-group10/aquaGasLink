package com.aquagaslink.order_management.queue.consumer;

import com.aquagaslink.order_management.model.OrderStatus;
import com.aquagaslink.order_management.queue.dto.delivery.DeliveryToOrderIn;
import com.aquagaslink.order_management.queue.dto.product.ProductToOrderIn;
import com.aquagaslink.order_management.service.OrderService;
import com.rabbitmq.client.Channel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductEventListenerConfigTest {
    @Mock
    private Channel channel;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private ProductEventListenerConfig rabbitMQReceiver;

    private Consumer<Message<ProductToOrderIn>> productToProductEventListener;

    private AutoCloseable mocks;

    @BeforeEach
    void setup() {
        mocks = MockitoAnnotations.openMocks(this);
        productToProductEventListener = rabbitMQReceiver.productToOrderEventListener();
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

    @Test
    void orderToProductEventListener_success() throws IOException {

        ProductToOrderIn payload = new ProductToOrderIn(UUID.randomUUID(), UUID.randomUUID(),"name","product",5,new BigDecimal("10.0"),false,"");

        Map<String, Object> headers = new HashMap<>();
        headers.put("amqp_deliveryTag", 1L);
        headers.put("amqp_channel", channel);

        Message<ProductToOrderIn> message = MessageBuilder.withPayload(payload)
                .copyHeaders(headers)
                .build();


        doNothing().when(channel).basicAck(1L, false);

        productToProductEventListener.accept(message);


        verify(channel, times(1)).basicAck(1L, false);
    }
}