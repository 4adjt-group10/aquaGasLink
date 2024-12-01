//package com.aquagaslink.product.queue.producer;
//
//import com.aquagaslink.product.queue.config.QueueProperties;
//import com.aquagaslink.product.queue.dto.ProductToOrderOut;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.cloud.stream.function.StreamBridge;
//
//import java.math.BigDecimal;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class ProductEventgatewayWithStreamBridgeTest {
//    @Mock
//    private StreamBridge streamBridge;
//    @Mock
//    private QueueProperties queueProperties;
//    @InjectMocks
//    private ProductEventgatewayWithStreamBridge productEventgatewayWithStreamBridge;
//
//    private AutoCloseable mocks;
//
//    @BeforeEach
//    void setup() {
//        mocks = MockitoAnnotations.openMocks(this);
//        productEventgatewayWithStreamBridge = new ProductEventgatewayWithStreamBridge(streamBridge, queueProperties);
//    }
//
//    @AfterEach
//    void tearDown() throws Exception {
//        mocks.close();
//    }
//
//    @Test
//    void testSendOrderEvent() {
//        ProductToOrderOut message = new ProductToOrderOut(UUID.randomUUID(), UUID.randomUUID(), "product name", 5, new BigDecimal("10.00"), true,"teste");
//        String channelName = "appOrderChannel";
//        when(queueProperties.getAppOrderChannel()).thenReturn(channelName);
//        productEventgatewayWithStreamBridge.sendOrderEvent(message);
//        verify(streamBridge, times(1)).send(channelName, message);
//    }
//}
