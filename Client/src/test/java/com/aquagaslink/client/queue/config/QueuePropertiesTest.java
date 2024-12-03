package com.aquagaslink.client.queue.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class QueuePropertiesTest {

    @Test
    public void testDefaultConstructor() {
        QueueProperties queueProperties = new QueueProperties();
        assertEquals("clientToOrderEventListener-out-0", queueProperties.getAppClientChannel());
    }

    @Test
    public void testParameterizedConstructor() {
        String appProductChannel = "test-channel";
        QueueProperties queueProperties = new QueueProperties(appProductChannel, null, null);
        assertEquals(appProductChannel, queueProperties.getAppClientChannel());
    }

    @Test
    public void testSetAppProductChannel() {
        QueueProperties queueProperties = new QueueProperties();
        String newChannel = "new-test-channel";
        queueProperties.setAppProductChannel(newChannel);
        assertEquals(newChannel, queueProperties.getAppClientChannel());
    }
}
