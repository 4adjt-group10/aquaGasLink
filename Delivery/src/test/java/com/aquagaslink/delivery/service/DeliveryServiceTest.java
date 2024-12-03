package com.aquagaslink.delivery.service;

import com.aquagaslink.delivery.controller.dto.DriverLocationForm;
import com.aquagaslink.delivery.controller.dto.RoutOutput;
import com.aquagaslink.delivery.infrastructure.DeliveryRepository;
import com.aquagaslink.delivery.model.*;
import com.aquagaslink.delivery.queue.DeliveryEventGateway;
import com.aquagaslink.delivery.queue.dto.DeliveryToOrderOut;
import com.aquagaslink.delivery.queue.dto.OrderToDeliveryIn;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.UUID;

import static com.aquagaslink.delivery.model.DeliveryPersonStatus.AVAILABLE;
import static com.aquagaslink.delivery.model.DeliveryStatus.IN_PROGRESS;
import static com.aquagaslink.delivery.model.DeliveryStatus.PENDING;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeliveryServiceTest {

    @Mock
    private DeliveryRepository deliveryRepository;

    @Mock
    private DeliveryPersonService deliveryPersonService;

    @Mock
    private DeliveryEventGateway deliveryEventGateway;

    @Mock
    private Logger logger;

    @InjectMocks
    private DeliveryService deliveryService;

    private AutoCloseable mocks;

    @BeforeEach
    void setup() {
        mocks = MockitoAnnotations.openMocks(this);
        deliveryService = new DeliveryService(deliveryRepository, deliveryPersonService, deliveryEventGateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

    @Test
    void testTracking_withCoordinates() {
        String orderId = UUID.randomUUID().toString();
        DriverLocationForm form = new DriverLocationForm("40.712776", "-74.005974", null);  // Coordinates for New York City

        DeliveryClient client = new DeliveryClient(UUID.randomUUID(), "cliente", new ClientAddress("10001", "5th Ave", "1234567890", "New York", "NY", "1", "USA"));

        DeliveryProduct deliveryProduct = new DeliveryProduct("product", new BigDecimal("10.0"));
        DeliveryPerson deliveryPerson1 = new DeliveryPerson("name entregador", "email@email.com", "31325646", DeliveryPersonStatus.BUSY, "yayhs");
        Delivery delivery = new Delivery(UUID.randomUUID(), deliveryPerson1, UUID.fromString(orderId), deliveryProduct, client, "40.712776", "-74.005974", IN_PROGRESS, "");
        delivery.setStatus(IN_PROGRESS);

        when(deliveryRepository.findByOrderId(any(UUID.class))).thenReturn(Optional.of(delivery));
        when(deliveryRepository.save(any(Delivery.class))).thenReturn(delivery);

        RoutOutput result = deliveryService.tracking(orderId, form);

        assertNotNull(result);
    }

    @Test
    void testTracking_withAddress() {
        String orderId = UUID.randomUUID().toString();
        ClientAddress address = new ClientAddress("10001", "5th Ave", "1234567890", "New York", "NY", "1", "USA");
        DriverLocationForm form = new DriverLocationForm(null, null, address);
        DeliveryPerson deliveryPerson1 = new DeliveryPerson("name entregador", "email@email.com", "31325646", DeliveryPersonStatus.BUSY, "yayhs");
        DeliveryProduct deliveryProduct = new DeliveryProduct("product", new BigDecimal("10.0"));

        DeliveryClient client = new DeliveryClient(UUID.randomUUID(), "cliente", new ClientAddress("10001", "5th Ave", "1234567890", "New York", "NY", "1", "USA"));
        Delivery delivery = new Delivery(UUID.randomUUID(), deliveryPerson1, UUID.fromString(orderId), deliveryProduct, client, "40.712776", "-74.005974", IN_PROGRESS, "");
        delivery.setStatus(IN_PROGRESS);

        when(deliveryRepository.findByOrderId(any(UUID.class))).thenReturn(Optional.of(delivery));

        RoutOutput result = deliveryService.tracking(orderId, form);

        assertNotNull(result);
    }

    @Test
    void testGetTrackingByClient_success() {
        String orderId = UUID.randomUUID().toString();

        UUID clientId = UUID.randomUUID();
        DeliveryPerson deliveryPerson1 = new DeliveryPerson("name entregador", "email@email.com", "31325646", DeliveryPersonStatus.BUSY, "yayhs");
        DeliveryProduct deliveryProduct = new DeliveryProduct("product", new BigDecimal("10.0"));
        DeliveryClient client = new DeliveryClient(UUID.randomUUID(), "cliente", new ClientAddress("10001", "5th Ave", "1234567890", "New York", "NY", "1", "USA"));
        Delivery delivery = new Delivery(UUID.randomUUID(), deliveryPerson1, UUID.fromString(orderId), deliveryProduct, client, "40.712776", "-74.005974", IN_PROGRESS, "");
        delivery.setStatus(IN_PROGRESS);
        delivery.setLatitude("40.712776");
        delivery.setLongitude("-74.005974");

        when(deliveryRepository.findByClientIdAndStatus(any(String.class), eq(IN_PROGRESS))).thenReturn(Optional.of(delivery));

        RoutOutput result = deliveryService.getTrackingByClient(clientId);

        assertNotNull(result);
    }

    @Test
    void testGetTrackingByClient_notFound() {
        UUID clientId = UUID.randomUUID();

        when(deliveryRepository.findByClientIdAndStatus(any(String.class), eq(IN_PROGRESS))).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> deliveryService.getTrackingByClient(clientId));
    }

    @Test
    void testGetTrackingByClient_trackingUnavailable() {
        String orderId = UUID.randomUUID().toString();

        UUID clientId = UUID.randomUUID();
        DeliveryClient client = new DeliveryClient(UUID.randomUUID(), "cliente", new ClientAddress("10001", "5th Ave", "1234567890", "New York", "NY", "1", "USA"));

        DeliveryPerson deliveryPerson1 = new DeliveryPerson("name entregador", "email@email.com", "31325646", DeliveryPersonStatus.BUSY, "yayhs");
        DeliveryProduct deliveryProduct = new DeliveryProduct("product", new BigDecimal("10.0"));
        Delivery delivery = new Delivery(UUID.randomUUID(), deliveryPerson1, UUID.fromString(orderId), deliveryProduct, client, null, null, IN_PROGRESS, "");
        delivery.setStatus(IN_PROGRESS);

        when(deliveryRepository.findByClientIdAndStatus(any(String.class), eq(IN_PROGRESS))).thenReturn(Optional.of(delivery));

        assertThrows(EntityNotFoundException.class, () -> deliveryService.getTrackingByClient(clientId));
    }

    @Test
    void testCreateDelivery() {
        OrderToDeliveryIn payload = new OrderToDeliveryIn(UUID.randomUUID(), UUID.randomUUID(), "clientName", new ClientAddress("123456", "Main Street", "123456789", "ClientCity", "ClientState", "123", "ClientCountry"), "productName", new BigDecimal("100.0"));
        deliveryService.createDelivery(payload);
        ArgumentCaptor<Delivery> deliveryCaptor = ArgumentCaptor.forClass(Delivery.class);
        verify(deliveryRepository, times(1)).save(deliveryCaptor.capture());
        Delivery capturedDelivery = deliveryCaptor.getValue();
        assertEquals(payload.orderId(), capturedDelivery.getOrderId());
        assertEquals(payload.clientId(), capturedDelivery.getDeliveryClient().getClientId());
        assertEquals(payload.clientName(), capturedDelivery.getDeliveryClient().getName());
        assertEquals(payload.address(), capturedDelivery.getDeliveryClient().getAddress());
        assertEquals(payload.productName(), capturedDelivery.getProduct().getProductName());
        assertEquals(PENDING, capturedDelivery.getStatus());
    }

    @Test
    void testFinishDeliveryAndSendToOrderNotFound() {
        UUID deliveryId = UUID.randomUUID();
        DeliveryFinishStatus finishStatus = DeliveryFinishStatus.COMPLETED;
        when(deliveryRepository.findById(deliveryId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> deliveryService.finishDeliveryAndSendToOrder(deliveryId, finishStatus));
        verify(deliveryRepository, times(1)).findById(deliveryId);
        verify(deliveryPersonService, times(0)).findById(any(UUID.class));
        verify(deliveryPersonService, times(0)).save(any(DeliveryPerson.class));
        verify(deliveryRepository, times(0)).save(any(Delivery.class));
        verify(deliveryEventGateway, times(0)).sendOrderEvent(any(DeliveryToOrderOut.class));
    }

}
