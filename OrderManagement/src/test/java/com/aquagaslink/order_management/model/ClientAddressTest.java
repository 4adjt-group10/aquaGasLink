package com.aquagaslink.order_management.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientAddressTest {

    @Test
    void testDefaultConstructor() {
        ClientAddress clientAddress = new ClientAddress();
        assertNotNull(clientAddress);
    }

    @Test
    void testParameterizedConstructor() {
        ClientAddress clientAddress = new ClientAddress("123456", "Main Street", "123456789", "ClientCity", "ClientState", "123", "ClientCountry");
        assertNotNull(clientAddress);
        assertEquals("123456", clientAddress.getPostalCode());
        assertEquals("Main Street", clientAddress.getStreet());
        assertEquals("123456789", clientAddress.getClientPhone());
        assertEquals("ClientCity", clientAddress.getClientCity());
        assertEquals("ClientState", clientAddress.getClientState());
        assertEquals("123", clientAddress.getClientNumber());
        assertEquals("ClientCountry", clientAddress.getClientCountry());
    }

    @Test
    void testGetters() {
        ClientAddress clientAddress = new ClientAddress("123456", "Main Street", "123456789", "ClientCity", "ClientState", "123", "ClientCountry");
        assertEquals("123456", clientAddress.getPostalCode());
        assertEquals("Main Street", clientAddress.getStreet());
        assertEquals("123456789", clientAddress.getClientPhone());
        assertEquals("ClientCity", clientAddress.getClientCity());
        assertEquals("ClientState", clientAddress.getClientState());
        assertEquals("123", clientAddress.getClientNumber());
        assertEquals("ClientCountry", clientAddress.getClientCountry());
    }
}