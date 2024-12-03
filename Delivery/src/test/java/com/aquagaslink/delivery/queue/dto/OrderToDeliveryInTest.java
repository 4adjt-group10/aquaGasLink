package com.aquagaslink.delivery.queue.dto;

import com.aquagaslink.delivery.model.ClientAddress;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OrderToDeliveryInTest {

    @Test
    void testRecordValues() {
        UUID orderId = UUID.randomUUID();
        UUID clientId = UUID.randomUUID();
        String clientName = "John Doe";
        ClientAddress address = new ClientAddress(
                "123456",
                "Main Street",
                "123456789",
                "ClientCity",
                "ClientState",
                "123",
                "ClientCountry"
        );
        String productName = "Product A";
        BigDecimal productPrice = new BigDecimal("99.99");

        OrderToDeliveryIn orderToDeliveryIn = new OrderToDeliveryIn(orderId, clientId, clientName, address, productName, productPrice);

        assertNotNull(orderToDeliveryIn);
        assertEquals(orderId, orderToDeliveryIn.orderId());
        assertEquals(clientId, orderToDeliveryIn.clientId());
        assertEquals(clientName, orderToDeliveryIn.clientName());
        assertEquals(address, orderToDeliveryIn.address());
        assertEquals(productName, orderToDeliveryIn.productName());
        assertEquals(productPrice, orderToDeliveryIn.productPrice());
    }

    @Test
    void testEqualsAndHashCode() {
        UUID orderId1 = UUID.randomUUID();
        UUID clientId1 = UUID.randomUUID();
        String clientName1 = "John Doe";
        ClientAddress address1 = new ClientAddress(
                "123456",
                "Main Street",
                "123456789",
                "ClientCity",
                "ClientState",
                "123",
                "ClientCountry"
        );

        String productName1 = "Product A";
        BigDecimal productPrice1 = new BigDecimal("99.99");

        OrderToDeliveryIn orderToDeliveryIn1 = new OrderToDeliveryIn(orderId1, clientId1, clientName1, address1, productName1, productPrice1);
        OrderToDeliveryIn orderToDeliveryIn2 = new OrderToDeliveryIn(orderId1, clientId1, clientName1, address1, productName1, productPrice1);

        assertEquals(orderToDeliveryIn1, orderToDeliveryIn2);
        assertEquals(orderToDeliveryIn1.hashCode(), orderToDeliveryIn2.hashCode());

    }
}
