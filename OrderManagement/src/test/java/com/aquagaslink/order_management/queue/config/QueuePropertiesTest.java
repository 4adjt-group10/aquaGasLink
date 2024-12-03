package com.aquagaslink.order_management.queue.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals("orderToProductEventListener-out-0", defaultQueueProperties.getAppProductChannel());
        assertEquals("orderToDeliveryEventListener-out-0", defaultQueueProperties.getAppDeliveryChannel());
        assertEquals("orderToClientEventListener-out-0", defaultQueueProperties.getAppClientChannel());
    }

    @Test
    void testParameterizedConstructor() {
        QueueProperties paramQueueProperties = new QueueProperties("productChannel", "clientChannel", "deliveryChannel");
        assertNotNull(paramQueueProperties);
        assertEquals("deliveryChannel", paramQueueProperties.getAppClientChannel());
        assertEquals("productChannel", paramQueueProperties.getAppProductChannel());
        assertEquals("clientChannel", paramQueueProperties.getAppDeliveryChannel());
    }

    @Test
    void testGettersAndSetters() {
        queueProperties.setAppProductChannel("productChannel");
        queueProperties.setAppClientChannel("clientChannel");
        queueProperties.setAppDeliveryChannel("deliveryChannel");

        assertEquals("productChannel", queueProperties.getAppProductChannel());
        assertEquals("clientChannel", queueProperties.getAppClientChannel());
        assertEquals("deliveryChannel", queueProperties.getAppDeliveryChannel());
    }
}