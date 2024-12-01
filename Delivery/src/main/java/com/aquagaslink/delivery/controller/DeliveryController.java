package com.aquagaslink.delivery.controller;

import com.aquagaslink.delivery.controller.dto.DriverLocationForm;
import com.aquagaslink.delivery.controller.dto.RoutOutput;
import com.aquagaslink.delivery.model.DeliveryStatus;
import com.aquagaslink.delivery.service.DeliveryPersonService;
import com.aquagaslink.delivery.service.DeliveryService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {

    final DeliveryService deliveryService;
    private final DeliveryPersonService deliveryPersonService;

    public DeliveryController(DeliveryService deliveryService, DeliveryPersonService deliveryPersonService) {
        this.deliveryService = deliveryService;
        this.deliveryPersonService = deliveryPersonService;
    }

    @PostMapping("/tracking/{orderId}")
    public RoutOutput tracking(@PathVariable String orderId, @RequestBody DriverLocationForm driverLocationForm) {
        return deliveryService.tracking(orderId, driverLocationForm);
    }

    @PutMapping("/update-status/{deliveryId}")
    public void updateStatus(@PathVariable UUID deliveryId, @RequestParam DeliveryStatus status) {
        deliveryService.updateStatusAndSendToOrder(deliveryId, status);
    }

    @GetMapping("/track-by-client/{clientId}")
    public RoutOutput trackByClient(@PathVariable UUID clientId) {
        return deliveryService.getTrackingByClient(clientId);
    }
}
