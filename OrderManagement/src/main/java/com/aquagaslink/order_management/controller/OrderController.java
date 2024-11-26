package com.aquagaslink.order_management.controller;

import com.aquagaslink.order_management.controller.dto.OrderDto;
import com.aquagaslink.order_management.controller.dto.OrderFormDto;
import com.aquagaslink.order_management.model.OrderStatus;
import com.aquagaslink.order_management.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequestMapping("/order")
public class OrderController {

    private static final String PAGEABLE_DESCRIPTION = """
        Accepts sorting parameters passing them in the URL, such as '?page=2&size=5&sort=createdAt,desc'
         to fetch the third page (page count starts at 0), with 5 records per page, sorted by createdAt in descending order.
    """;

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/search/{id}")
    @Operation(summary = "Get order by id")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable UUID id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping("/list-all/{clientId}")
    @Operation(summary = "List all orders by client id", description = PAGEABLE_DESCRIPTION)
    public ResponseEntity<Page<OrderDto>> listOrders(@PathVariable UUID clientId,
                                                     @Nullable @PageableDefault(size = 5, sort = "createdAt", direction = DESC) Pageable pageable) {
        return ResponseEntity.ok(orderService.listOrdersByClientId(clientId, pageable));
    }

    @PostMapping("/create")
    @Operation(summary = "Create order")
    public ResponseEntity<OrderDto> createOrder(@RequestBody @Valid OrderFormDto formDto) {
        return ResponseEntity.ok(orderService.createOrder(formDto));
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update order")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable UUID id, @RequestBody @Valid OrderFormDto formDto) {
        return ResponseEntity.ok(orderService.updateOrder(id, formDto));
    }

    @PatchMapping("/update-status/{id}")
    @Operation(summary = "Update order status")
    public ResponseEntity<OrderDto> updateOrderStatus(@PathVariable UUID id, @RequestParam OrderStatus status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, status));
    }

    @PostMapping("/send-product")
    @Operation(summary = "Send product message to queue")
    public ResponseEntity<Void> sendProductMessage(@RequestBody String message) {
        return ResponseEntity.ok().build();
    }
}