package com.aquagaslink.product.queue.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class QueuePropertiesTest {

    private QueueProperties queueProperties;

    @BeforeEach
    void setUp() {
        queueProperties = new QueueProperties();
    }

    @Test
    void testDefaultConstructor() {
        QueueProperties defaultQueueProperties = new QueueProperties();
        assertNotNull(defaultQueueProperties);
        assertEquals("productToOrderEventListener-out-0", defaultQueueProperties.getAppOrderChannel());
        assertEquals("productToClientEventListener-out-0", defaultQueueProperties.getAppClientChannel());
        assertEquals("productToDeliveryEventListener-out-0", defaultQueueProperties.getAppDeliveryChannel());
    }

    @Test
    void testParameterizedConstructor() {
        QueueProperties paramQueueProperties = new QueueProperties("orderChannel", "clientChannel", "deliveryChannel");
        assertNotNull(paramQueueProperties);
        assertEquals("orderChannel", paramQueueProperties.getAppOrderChannel());
        assertEquals("clientChannel", paramQueueProperties.getAppClientChannel());
        assertEquals("deliveryChannel", paramQueueProperties.getAppDeliveryChannel());
    }

    @Test
    void testGettersAndSetters() {
        queueProperties.setAppOrderChannel("orderChannel");
        queueProperties.setAppClientChannel("clientChannel");
        queueProperties.setAppDeliveryChannel("deliveryChannel");

        assertEquals("orderChannel", queueProperties.getAppOrderChannel());
        assertEquals("clientChannel", queueProperties.getAppClientChannel());
        assertEquals("deliveryChannel", queueProperties.getAppDeliveryChannel());
    }
}
