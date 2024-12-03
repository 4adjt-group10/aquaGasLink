package com.aquagaslink.delivery.queue.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class QueuePropertiesTest {

    @Test
    void testDefaultConstructor() {
        QueueProperties queueProperties = new QueueProperties();
        assertNotNull(queueProperties);
        assertEquals("deliveryToOrderEventListener-out-0", queueProperties.getAppOrderChannel());
    }

    @Test
    void testParameterizedConstructor() {
        QueueProperties queueProperties = new QueueProperties("customOrderChannel");
        assertNotNull(queueProperties);
        assertEquals("customOrderChannel", queueProperties.getAppOrderChannel());
    }

    @Test
    void testGettersAndSetters() {
        QueueProperties queueProperties = new QueueProperties();
        queueProperties.setAppOrderChannel("newOrderChannel");

        assertEquals("newOrderChannel", queueProperties.getAppOrderChannel());
    }
}
