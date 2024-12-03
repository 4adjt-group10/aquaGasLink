package com.aquagaslink.order_management.queue.consumer;

import com.aquagaslink.order_management.model.OrderStatus;
import com.aquagaslink.order_management.queue.dto.delivery.DeliveryToOrderIn;
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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeliveryEventListenerConfigTest {
    @Mock
    private Channel channel;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private DeliveryEventListenerConfig rabbitMQReceiver;

    private Consumer<Message<DeliveryToOrderIn>> deliveryToProductEventListener;

    private AutoCloseable mocks;

    @BeforeEach
    void setup() {
        mocks = MockitoAnnotations.openMocks(this);
        deliveryToProductEventListener = rabbitMQReceiver.deliveryToOrderEventListener();
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

    @Test
    void orderToProductEventListener_success() throws IOException {

        DeliveryToOrderIn payload = new DeliveryToOrderIn(UUID.randomUUID(), OrderStatus.COMPLETED);

        Map<String, Object> headers = new HashMap<>();
        headers.put("amqp_deliveryTag", 1L);
        headers.put("amqp_channel", channel);

        Message<DeliveryToOrderIn> message = MessageBuilder.withPayload(payload)
                .copyHeaders(headers)
                .build();


        doNothing().when(channel).basicAck(1L, false);

        deliveryToProductEventListener.accept(message);


        verify(channel, times(1)).basicAck(1L, false);
    }

}