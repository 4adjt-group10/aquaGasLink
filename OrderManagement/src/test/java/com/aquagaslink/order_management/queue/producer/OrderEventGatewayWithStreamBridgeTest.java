package com.aquagaslink.order_management.queue.producer;

import com.aquagaslink.order_management.model.ClientAddress;
import com.aquagaslink.order_management.queue.config.QueueProperties;
import com.aquagaslink.order_management.queue.dto.client.OrderToClientOut;
import com.aquagaslink.order_management.queue.dto.delivery.OrderToDeliveryOut;
import com.aquagaslink.order_management.queue.dto.product.OrderToProductOut;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.cloud.stream.function.StreamBridge;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.Mockito.*;

class OrderEventGatewayWithStreamBridgeTest {
    @Mock
    private StreamBridge streamBridge;
    @Mock
    private QueueProperties queueProperties;
    @InjectMocks
    private OrderEventGatewayWithStreamBridge orderEventgatewayWithStreamBridge;

    private AutoCloseable mocks;

    @BeforeEach
    void setup() {
        mocks = MockitoAnnotations.openMocks(this);
        orderEventgatewayWithStreamBridge = new OrderEventGatewayWithStreamBridge(streamBridge, queueProperties);
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

    @Test
    void testSendDeliveryEvent() {
        OrderToDeliveryOut message = new OrderToDeliveryOut(UUID.randomUUID(), UUID.randomUUID(), "name", null, "product", new BigDecimal("20.0"));
        String channelName = "deliveryChannel";
        when(queueProperties.getAppDeliveryChannel()).thenReturn(channelName);
        orderEventgatewayWithStreamBridge.sendDeliveryEvent(message);
        verify(streamBridge, times(1)).send(channelName, message);

    }

    @Test
    void testSendClientEvent() {
        OrderToClientOut message = new OrderToClientOut(UUID.randomUUID(), UUID.randomUUID());
        String channelName = "clientChannel";
        when(queueProperties.getAppClientChannel()).thenReturn(channelName);
        orderEventgatewayWithStreamBridge.sendClientEvent(message);
        verify(streamBridge, times(1)).send(channelName, message);
    }

    @Test
    void testSendProductEvent() {
        OrderToProductOut message = new OrderToProductOut(UUID.randomUUID(),5,UUID.randomUUID(),"name");
        String channelName = "productChannel";
        when(queueProperties.getAppProductChannel()).thenReturn(channelName);
        orderEventgatewayWithStreamBridge.sendProductEvent(message);
        verify(streamBridge, times(1)).send(channelName, message);
    }

}

