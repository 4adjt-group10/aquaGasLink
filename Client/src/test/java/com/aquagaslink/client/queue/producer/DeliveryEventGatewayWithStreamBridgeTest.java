package com.aquagaslink.client.queue.producer;

import com.aquagaslink.client.queue.config.QueueProperties;
import com.aquagaslink.client.queue.dto.ClientToOrderOut;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;
import org.springframework.cloud.stream.function.StreamBridge;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class DeliveryEventGatewayWithStreamBridgeTest {

    @Mock
    private StreamBridge streamBridge;

    @Mock
    private QueueProperties queueProperties;

    @InjectMocks
    private DeliveryEventGatewayWithStreamBridge deliveryEventGateway;

    @Test
    public void testSendOrderEvent() throws Exception {
        ClientToOrderOut message = mock(ClientToOrderOut.class);

        when(queueProperties.getAppClientChannel()).thenReturn("order-events");

        deliveryEventGateway.sendOrderEvent(message);

        verify(streamBridge).send("order-events", message);


    }
}
