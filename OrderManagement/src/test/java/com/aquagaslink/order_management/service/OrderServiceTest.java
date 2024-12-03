package com.aquagaslink.order_management.service;

import com.aquagaslink.order_management.controller.dto.OrderDto;
import com.aquagaslink.order_management.controller.dto.OrderFormDto;
import com.aquagaslink.order_management.helper.ClientOrderHelper;
import com.aquagaslink.order_management.infrastructure.ClientOrderRepository;
import com.aquagaslink.order_management.model.ClientAddress;
import com.aquagaslink.order_management.model.ClientOrder;
import com.aquagaslink.order_management.model.OrderStatus;
import com.aquagaslink.order_management.queue.OrderEventGateway;
import com.aquagaslink.order_management.queue.dto.client.ClientToOrderIn;
import com.aquagaslink.order_management.queue.dto.client.OrderToClientOut;
import com.aquagaslink.order_management.queue.dto.product.OrderToProductOut;
import com.aquagaslink.order_management.queue.dto.product.ProductToOrderIn;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @Mock
    private ClientOrderRepository clientOrderRepository;

    private OrderService service;

    @Mock
    private OrderEventGateway orderEventGateway;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        service = new OrderService(clientOrderRepository, orderEventGateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Test
    void testCreateOrder() {
        OrderFormDto formDto = new OrderFormDto(UUID.randomUUID(), 5, new BigDecimal("10.0"), UUID.randomUUID()); // Exemplo de dados

        ClientOrder clientOrder = new ClientOrder(formDto);
        when(clientOrderRepository.save(any(ClientOrder.class))).thenReturn(clientOrder);
        service.createOrder(formDto);
        verify(clientOrderRepository, times(1)).save(any(ClientOrder.class));
        verify(orderEventGateway, times(1)).sendClientEvent(any(OrderToClientOut.class));
    }

    @Test
    void shouldFindOrderById() {
        UUID orderId = UUID.randomUUID();
        ClientOrder clientOrder = mock(ClientOrder.class);
        when(clientOrderRepository.findById(orderId)).thenReturn(Optional.of(clientOrder));
        when(clientOrder.getId()).thenReturn(orderId);

        OrderDto result = service.getOrderById(orderId);

        assertNotNull(result);
        assertEquals(orderId, result.id());
        verify(clientOrderRepository, times(1)).findById(orderId);
    }


    @Test
    void shouldListOrdersByClientId() {
        UUID clientId = UUID.randomUUID();
        Pageable pageable = PageRequest.of(0, 10);
        ClientOrder order1 = mock(ClientOrder.class);
        ClientOrder order2 = mock(ClientOrder.class);
        Page<ClientOrder> page = new PageImpl<>(Arrays.asList(order1, order2), pageable, 2);

        when(clientOrderRepository.findAllByClientId(clientId, pageable)).thenReturn(page);

        Page<OrderDto> result = service.listOrdersByClientId(clientId, pageable);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        verify(clientOrderRepository, times(1)).findAllByClientId(clientId, pageable);
    }

    @Test
    void shouldUpdateOrder() {
        UUID orderId = UUID.randomUUID();
        OrderFormDto formDto = new OrderFormDto(UUID.randomUUID(), 1, BigDecimal.ONE, UUID.randomUUID());
        ClientOrder clientOrder = mock(ClientOrder.class);

        when(clientOrderRepository.findById(orderId)).thenReturn(Optional.of(clientOrder));
        when(clientOrderRepository.save(clientOrder)).thenReturn(clientOrder);

        OrderDto result = service.updateOrder(orderId, formDto);

        assertNotNull(result);
        verify(clientOrderRepository, times(1)).findById(orderId);
        verify(clientOrderRepository, times(1)).save(clientOrder);
    }

    @Test
    void shouldUpdateOrderStatus() {
        UUID orderId = UUID.randomUUID();
        OrderStatus newStatus = OrderStatus.COMPLETED;
        ClientOrder clientOrder = mock(ClientOrder.class);

        when(clientOrderRepository.findById(orderId)).thenReturn(Optional.of(clientOrder));
        when(clientOrderRepository.save(clientOrder)).thenReturn(clientOrder);

        OrderDto result = service.updateOrderStatus(orderId, newStatus);

        assertNotNull(result);
        verify(clientOrderRepository, times(1)).findById(orderId);
        verify(clientOrderRepository, times(1)).save(clientOrder);
        verify(clientOrder).setStatus(newStatus);
    }

    @Test
    void shouldValidateClientWithError() {
        UUID orderId = UUID.randomUUID();
        UUID id = UUID.randomUUID();
        ClientToOrderIn payload = new ClientToOrderIn(id, orderId, "Client name", "Client phone", null, true, "Client error");

        ClientOrder clientOrder = new ClientOrder(UUID.randomUUID(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                OrderStatus.COMPLETED,
                UUID.randomUUID(),
                null,
                UUID.randomUUID(),
                5,
                new BigDecimal("10.0"),
                true,
                true,
                "teste");

        when(clientOrderRepository.findById(orderId)).thenReturn(Optional.of(clientOrder));

        service.validateClient(payload);

        verify(clientOrderRepository, times(1)).findById(orderId);
    }


    @Test
    void shouldValidateClientWithOutError() {
        UUID orderId = UUID.randomUUID();
        UUID id = UUID.randomUUID();
        ClientToOrderIn payload = new ClientToOrderIn(id, orderId, "Client name", "Client phone", null, false, "Client error");

        ClientOrder clientOrder = new ClientOrder(UUID.randomUUID(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                OrderStatus.COMPLETED,
                UUID.randomUUID(),
                null,
                UUID.randomUUID(),
                5,
                new BigDecimal("10.0"),
                false,
                false,
                "teste");

        when(clientOrderRepository.findById(orderId)).thenReturn(Optional.of(clientOrder));

        service.validateClient(payload);

        verify(clientOrderRepository, times(1)).findById(orderId);
    }


    @Test
    void testValidateProductWithError() {
        ProductToOrderIn payload = new ProductToOrderIn(UUID.randomUUID(), UUID.randomUUID(),"name client", "Product error", 5, new BigDecimal("10.0"),true, "Product error");

        ClientOrder clientOrder = new ClientOrder(payload.orderId(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                OrderStatus.COMPLETED,
                UUID.randomUUID(),
                null,
                UUID.randomUUID(),
                5,
                new BigDecimal("10.0"),
                false,
                false,
                "Initial observation. ");
        when(clientOrderRepository.findById(any(UUID.class))).thenReturn(Optional.of(clientOrder));
        service.validateProduct(payload);
        verify(clientOrderRepository, times(1)).saveAndFlush(any(ClientOrder.class));
    }

    @Test
    void testValidateProductWithoutError() {
        ProductToOrderIn payload = new ProductToOrderIn(UUID.randomUUID(), UUID.randomUUID(),"name client", "Product error", 5, new BigDecimal("10.0"),false, "Product error");
        ClientOrder clientOrder = new ClientOrder(payload.orderId(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                OrderStatus.COMPLETED,
                UUID.randomUUID(),
                null,
                UUID.randomUUID(),
                5,
                new BigDecimal("10.0"),
                false,
                false,
                "Initial observation. ");

        when(clientOrderRepository.findById(any(UUID.class))).thenReturn(Optional.of(clientOrder));
        service.validateProduct(payload);
        Assertions.assertEquals(OrderStatus.IN_PROGRESS, clientOrder.getStatus()); // ou qualquer status que se aplique
    }

    @Test
    void testValidateProductOrderNotFound() {
        ProductToOrderIn payload = new ProductToOrderIn(UUID.randomUUID(), UUID.randomUUID(),"name client", "Product error", 5, new BigDecimal("10.0"),false, "Product error");
        when(clientOrderRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        service.validateProduct(payload);
        verify(clientOrderRepository, never()).saveAndFlush(any(ClientOrder.class));
    }

}
