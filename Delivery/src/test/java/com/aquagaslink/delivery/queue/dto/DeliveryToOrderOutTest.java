package com.aquagaslink.delivery.queue.dto;

import com.aquagaslink.delivery.model.DeliveryFinishStatus;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DeliveryToOrderOutTest {

    @Test
    void testRecordValues() {
        UUID orderId = UUID.randomUUID();
        DeliveryFinishStatus status = DeliveryFinishStatus.COMPLETED;

        DeliveryToOrderOut deliveryToOrderOut = new DeliveryToOrderOut(orderId, status);

        assertNotNull(deliveryToOrderOut);
        assertEquals(orderId, deliveryToOrderOut.orderId());
        assertEquals(status, deliveryToOrderOut.status());
    }

    @Test
    void testEqualsAndHashCode() {
        UUID orderId1 = UUID.randomUUID();
        UUID orderId2 = UUID.randomUUID();
        DeliveryFinishStatus status1 = DeliveryFinishStatus.COMPLETED;
        DeliveryFinishStatus status2 = DeliveryFinishStatus.CANCELLED;

        DeliveryToOrderOut deliveryToOrderOut1 = new DeliveryToOrderOut(orderId1, status1);
        DeliveryToOrderOut deliveryToOrderOut2 = new DeliveryToOrderOut(orderId1, status1);


        assertEquals(deliveryToOrderOut1, deliveryToOrderOut2);
        assertEquals(deliveryToOrderOut1.hashCode(), deliveryToOrderOut2.hashCode());
    }
}
