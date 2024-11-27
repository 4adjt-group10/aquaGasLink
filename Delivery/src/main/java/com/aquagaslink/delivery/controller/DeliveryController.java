package com.aquagaslink.delivery.controller;

import com.aquagaslink.delivery.controller.dto.DriverLocationForm;
import com.aquagaslink.delivery.controller.dto.RoutOutput;
import com.aquagaslink.delivery.queue.DeliveryEventGateway;
import com.aquagaslink.delivery.service.DeliveryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {

    final DeliveryService deliveryService;
    private final DeliveryEventGateway orderEventGateway;

    public DeliveryController(DeliveryService deliveryService, DeliveryEventGateway deliveryEventGateway) {
        this.deliveryService = deliveryService;
        this.orderEventGateway = deliveryEventGateway;
    }

    @PostMapping("/driving/{orderId}")
    public RoutOutput driving(@PathVariable String orderId, @RequestBody DriverLocationForm driverLocationForm) {
        return deliveryService.driving(orderId, driverLocationForm);
    }

    @GetMapping("/teste")
    public void teste(){
        deliveryService.teste();
    }

    @PostMapping("/send-order-message")
    public String sendMessageToOrderQueue(@RequestParam String message) {
        for (int i = 0; i < 10; i++) {
            orderEventGateway.sendOrderEvent("Message teste " + i + " numero randomico :" + Math.random());
        }

        return "Delivery -- Mensagem enviada para a fila de order: " + message;
    }
}
