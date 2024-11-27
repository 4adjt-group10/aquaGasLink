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
import org.springframework.http.ResponseEntity;

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

    private ClientOrderHelper helper;

    @Test
    public void testGetOrderById() {
        UUID id = UUID.randomUUID();
        var orderDto = helper.createOrderDto(id);

        when(service.getOrderById(id)).thenReturn(orderDto);
        ResponseEntity<OrderDto> response = controller.getOrderById(id);

        verify(service).getOrderById(id);
        assertEquals(ResponseEntity.ok(orderDto), response);
    }

    @Test
    public void testCreateOrder() {
        var formDto = helper.createOrderFormDto("code");
        var orderDto = helper.createOrderDto(UUID.randomUUID());

        when(service.createOrder(formDto)).thenReturn(orderDto);
        ResponseEntity<OrderDto> response = controller.createOrder(formDto);

        verify(service).createOrder(formDto);
        assertEquals(ResponseEntity.ok(orderDto), response);
    }

    @Test
    public void testUpdateOrder() {
        UUID id = UUID.randomUUID();
        var formDto = helper.createOrderFormDto("code");
        var orderDto = helper.createOrderDto(id);

        when(service.updateOrder(id, formDto)).thenReturn(orderDto);
        ResponseEntity<OrderDto> response = controller.updateOrder(id, formDto);

        verify(service).updateOrder(id, formDto);
        assertEquals(ResponseEntity.ok(orderDto), response);
    }

    @Test
    public void testUpdateOrderStatus() {
        UUID id = UUID.randomUUID();
        OrderStatus status = OrderStatus.COMPLETED;
        var orderDto = helper.createOrderDto(id);

        when(service.updateOrderStatus(id, status)).thenReturn(orderDto);
        ResponseEntity<OrderDto> response = controller.updateOrderStatus(id, status);

        verify(service).updateOrderStatus(id, status);
        assertEquals(ResponseEntity.ok(orderDto), response);
    }
}