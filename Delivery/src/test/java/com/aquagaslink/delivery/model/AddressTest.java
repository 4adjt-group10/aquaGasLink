package com.aquagaslink.delivery.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AddressTest {

    @Test
    void testDefaultConstructor() {
        Address address = new Address();
        assertNotNull(address);
    }

    @Test
    void testParameterizedConstructor() {
        Address address = new Address(
                "Main Street",
                123,
                "Downtown",
                "CityName",
                "StateName",
                "CountryName",
                "123456"
        );

        assertNotNull(address);
        assertEquals("Main Street", address.getStreet());
        assertEquals(123, address.getNumber());
        assertEquals("Downtown", address.getNeighborhood());
        assertEquals("CityName", address.getCity());
        assertEquals("StateName", address.getState());
        assertEquals("CountryName", address.getCountry());
        assertEquals("123456", address.getZipCode());
    }

    @Test
    void testGetters() {
        Address address = new Address(
                "Main Street",
                123,
                "Downtown",
                "CityName",
                "StateName",
                "CountryName",
                "123456"
        );

        assertEquals("Main Street", address.getStreet());
        assertEquals(123, address.getNumber());
        assertEquals("Downtown", address.getNeighborhood());
        assertEquals("CityName", address.getCity());
        assertEquals("StateName", address.getState());
        assertEquals("CountryName", address.getCountry());
        assertEquals("123456", address.getZipCode());
    }
}
