package com.aquagaslink.order_management.queue.consumer;

import com.aquagaslink.order_management.model.ClientAddress;
import com.aquagaslink.order_management.queue.dto.client.ClientToOrderIn;
import com.aquagaslink.order_management.service.OrderService;
import com.rabbitmq.client.Channel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class ClientEventListenerConfigTest {

    @Mock
    private Channel channel;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private ClientEventListenerConfig rabbitMQReceiver;

    private Consumer<Message<ClientToOrderIn>> orderToProductEventListener;

    private AutoCloseable mocks;

    @BeforeEach
    void setup() {
        mocks = MockitoAnnotations.openMocks(this);
        orderToProductEventListener = rabbitMQReceiver.clientToOrderEventListener();
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

    @Test
    void orderToProductEventListener_success() throws IOException {
        UUID productId = UUID.randomUUID();
        ClientToOrderIn payload = new ClientToOrderIn(productId, UUID.randomUUID(),"teste", "321456323",null,false, "Client name");

        Map<String, Object> headers = new HashMap<>();
        headers.put("amqp_deliveryTag", 1L);
        headers.put("amqp_channel", channel);

        Message<ClientToOrderIn> message = MessageBuilder.withPayload(payload)
                .copyHeaders(headers)
                .build();


        doNothing().when(channel).basicAck(1L, false);

        orderToProductEventListener.accept(message);


        verify(channel, times(1)).basicAck(1L, false);
    }
}
