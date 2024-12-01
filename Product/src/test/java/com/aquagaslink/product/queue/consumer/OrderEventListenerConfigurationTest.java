package com.aquagaslink.product.queue.consumer;

import com.aquagaslink.product.queue.dto.OrderToProductIn;
import com.aquagaslink.product.service.ProductService;
import com.rabbitmq.client.Channel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderEventListenerConfigurationTest {

    @Mock
    private Channel channel;

    @Mock
    private ProductService productService;

    @InjectMocks
    private OrderEventListenerConfiguration rabbitMQReceiver;

    private Consumer<Message<OrderToProductIn>> orderToProductEventListener;

    private AutoCloseable mocks;

    @BeforeEach
    void setup() {
        mocks = MockitoAnnotations.openMocks(this);
        orderToProductEventListener = rabbitMQReceiver.orderToProductEventListener();
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

    @Test
    void orderToProductEventListener_success() throws IOException {
        UUID orderId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        OrderToProductIn payload = new OrderToProductIn(productId, 2, orderId, "Client name");

        Map<String, Object> headers = new HashMap<>();
        headers.put("amqp_deliveryTag", 1L);
        headers.put("amqp_channel", channel);

        Message<OrderToProductIn> message = MessageBuilder.withPayload(payload)
                .copyHeaders(headers)
                .build();


        doNothing().when(channel).basicAck(1L, false);

        orderToProductEventListener.accept(message);


        verify(channel, times(1)).basicAck(1L, false);
    }

}
