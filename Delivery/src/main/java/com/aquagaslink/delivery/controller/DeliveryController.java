package com.aquagaslink.delivery.controller;

import com.aquagaslink.delivery.controller.dto.DriverLocationForm;
import com.aquagaslink.delivery.controller.dto.RoutOutput;
import com.aquagaslink.delivery.model.DeliveryStatus;
import com.aquagaslink.delivery.service.DeliveryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {

    final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PostMapping("/tracking/{orderId}")
    @Operation(summary = "Tracking delivery by order id", description = "Can track by driver latitude and longitude or by address")
    public RoutOutput tracking(@PathVariable String orderId, @RequestBody DriverLocationForm driverLocationForm) {
        return deliveryService.tracking(orderId, driverLocationForm);
    }

    @PutMapping("/update-status/{deliveryId}")
    @Operation(summary = "Update delivery status", description = "Commonly used to finalize delivery (set do delivered)")
    public void updateStatus(@PathVariable UUID deliveryId, @RequestParam DeliveryStatus status) {
        deliveryService.updateStatusAndSendToOrder(deliveryId, status);
    }

    @GetMapping("/track-by-client/{clientId}")
    @Operation(summary = "Track delivery by client id")
    public RoutOutput trackByClient(@PathVariable UUID clientId) {
        return deliveryService.getTrackingByClient(clientId);
    }
}
