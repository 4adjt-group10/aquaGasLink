package com.aquagaslink.delivery.controller;

import com.aquagaslink.delivery.controller.dto.DriverLocationForm;
import com.aquagaslink.delivery.controller.dto.RoutOutput;
import com.aquagaslink.delivery.service.DeliveryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {

    final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PostMapping("/driving/{orderId}")
    public RoutOutput driving(@PathVariable String orderId, @RequestBody DriverLocationForm driverLocationForm) {
        return deliveryService.driving(orderId, driverLocationForm);
    }

    @GetMapping("/teste")
    public void teste(){
        deliveryService.teste();
    }
}
