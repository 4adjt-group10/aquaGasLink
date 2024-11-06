package com.aquagaslink.order_management.controller;

import com.aquagaslink.order_management.controller.dto.OrderDto;
import com.aquagaslink.order_management.controller.dto.OrderFormDto;
import com.aquagaslink.order_management.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/search/{id}")
    @Operation(description = "Get order by id")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable UUID id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping("/list-all/{clientId}")
    @Operation(description = "List all orders by client id")
    public ResponseEntity<List<OrderDto>> listOrders(@PathVariable UUID clientId) {
        return ResponseEntity.ok(orderService.listOrdersByClientId(clientId));
    }

    @PostMapping("/create")
    @Operation(description = "Create order")
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderFormDto formDto) {
        return ResponseEntity.ok(orderService.createOrder(formDto));
    }

    @PutMapping("/update/{id}")
    @Operation(description = "Update order")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable UUID id, @RequestBody OrderFormDto formDto) {
        return ResponseEntity.ok(orderService.updateOrder(id, formDto));
    }
}
