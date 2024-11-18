package com.aquagaslink.order_management.controller;

import com.aquagaslink.order_management.controller.dto.OrderDto;
import com.aquagaslink.order_management.helper.ClientOrderHelper;
import com.aquagaslink.order_management.model.OrderStatus;
import com.aquagaslink.order_management.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    @Mock
    private OrderService service;

    @InjectMocks
    private OrderController controller;

    @Autowired
    private ClientOrderHelper helper;

    @Test
    public void testSendProductMessage() {
        // Given
        String message = "test message";

        // When
        ResponseEntity<Void> response = controller.sendProductMessage(message);

        // Then
        verify(service).sendProduct(message);
        verify(service).sendDelivery(message);
        assertEquals(ResponseEntity.ok().build(), response);
    }

    @Test
    public void testGetOrderById() {
        // Given
        UUID id = UUID.randomUUID();
        var orderDto = helper.createOrderDto(id);

        // When
        when(service.getOrderById(id)).thenReturn(orderDto);
        ResponseEntity<OrderDto> response = controller.getOrderById(id);

        // Then
        verify(service).getOrderById(id);
        assertEquals(ResponseEntity.ok(orderDto), response);
    }

    @Test
    public void testCreateOrder() {
        // Given
        var formDto = helper.createOrderFormDto("code");
        var orderDto = helper.createOrderDto(UUID.randomUUID());

        // When
        when(service.createOrder(formDto)).thenReturn(orderDto);
        ResponseEntity<OrderDto> response = controller.createOrder(formDto);

        // Then
        verify(service).createOrder(formDto);
        assertEquals(ResponseEntity.ok(orderDto), response);
    }

    @Test
    public void testUpdateOrder() {
        // Given
        UUID id = UUID.randomUUID();
        var formDto = helper.createOrderFormDto("code");
        var orderDto = helper.createOrderDto(id);

        // When
        when(service.updateOrder(id, formDto)).thenReturn(orderDto);
        ResponseEntity<OrderDto> response = controller.updateOrder(id, formDto);

        // Then
        verify(service).updateOrder(id, formDto);
        assertEquals(ResponseEntity.ok(orderDto), response);
    }

    @Test
    public void testUpdateOrderStatus() {
        // Given
        UUID id = UUID.randomUUID();
        OrderStatus status = OrderStatus.COMPLETED;
        var orderDto = helper.createOrderDto(id);

        // When
        when(service.updateOrderStatus(id, status)).thenReturn(orderDto);
        ResponseEntity<OrderDto> response = controller.updateOrderStatus(id, status);

        // Then
        verify(service).updateOrderStatus(id, status);
        assertEquals(ResponseEntity.ok(orderDto), response);
    }
}