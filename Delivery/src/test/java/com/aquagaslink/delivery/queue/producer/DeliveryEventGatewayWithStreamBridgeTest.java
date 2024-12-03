package com.aquagaslink.delivery.queue.producer;

import com.aquagaslink.delivery.model.DeliveryFinishStatus;
import com.aquagaslink.delivery.queue.config.QueueProperties;
import com.aquagaslink.delivery.queue.dto.DeliveryToOrderOut;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.stream.function.StreamBridge;

import java.util.UUID;
import java.util.logging.Logger;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeliveryEventGatewayWithStreamBridgeTest {

    @Mock
    private StreamBridge streamBridge;

    @Mock
    private QueueProperties queueProperties;

    @Mock
    private Logger logger;

    @InjectMocks
    private DeliveryEventGatewayWithStreamBridge deliveryEventGatewayWithStreamBridge;

    private AutoCloseable mocks;

    @BeforeEach
    void setup() {
        mocks = MockitoAnnotations.openMocks(this);
        deliveryEventGatewayWithStreamBridge = new DeliveryEventGatewayWithStreamBridge(streamBridge, queueProperties);
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }


    @Test
    void testSendOrderEvent() {
        DeliveryToOrderOut message = new DeliveryToOrderOut(UUID.randomUUID(), DeliveryFinishStatus.COMPLETED);
        String channelName = "deliveryToOrderEventListener-out-0";

        when(queueProperties.getAppOrderChannel()).thenReturn(channelName);

        deliveryEventGatewayWithStreamBridge.sendOrderEvent(message);

        verify(streamBridge, times(1)).send(channelName, message);
    }
}
