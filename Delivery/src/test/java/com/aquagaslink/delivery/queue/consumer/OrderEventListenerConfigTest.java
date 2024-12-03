package com.aquagaslink.delivery.queue.consumer;

import com.aquagaslink.delivery.model.ClientAddress;
import com.aquagaslink.delivery.queue.dto.OrderToDeliveryIn;
import com.aquagaslink.delivery.service.DeliveryService;
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

import static org.mockito.Mockito.*;

class OrderEventListenerConfigTest {

    @Mock
    private Channel channel;

    @Mock
    private DeliveryService deliveryService;

    @InjectMocks
    private OrderEventListenerConfig rabbitMQReceiver;

    private Consumer<Message<OrderToDeliveryIn>> orderToProductEventListener;

    private AutoCloseable mocks;

    @BeforeEach
    void setup() {
        mocks = MockitoAnnotations.openMocks(this);
        orderToProductEventListener = rabbitMQReceiver.orderToDeliveryEventListener();
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

    @Test
    void orderToProductEventListener_success() throws IOException {
        UUID orderId = UUID.randomUUID();
        UUID clientId = UUID.randomUUID();

        OrderToDeliveryIn payload = new OrderToDeliveryIn(orderId, clientId, "clientName", null,"product name",new BigDecimal("10.0"));

        Map<String, Object> headers = new HashMap<>();
        headers.put("amqp_deliveryTag", 1L);
        headers.put("amqp_channel", channel);

        Message<OrderToDeliveryIn> message = MessageBuilder.withPayload(payload)
                .copyHeaders(headers)
                .build();


        doNothing().when(channel).basicAck(1L, false);

        orderToProductEventListener.accept(message);


        verify(channel, times(1)).basicAck(1L, false);
    }

}
