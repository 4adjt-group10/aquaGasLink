package com.aquagaslink.delivery.controller;

import com.aquagaslink.delivery.controller.dto.DriverLocationForm;
import com.aquagaslink.delivery.controller.dto.RoutOutput;
import com.aquagaslink.delivery.model.DeliveryFinishStatus;
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

    @PutMapping("/finish/{deliveryId}")
    @Operation(summary = "Finish delivery", description = "Update delivery status and send an order event")
    public void finishDelivery(@PathVariable UUID deliveryId, @RequestParam DeliveryFinishStatus status) {
        deliveryService.finishDeliveryAndSendToOrder(deliveryId, status);
    }

    @GetMapping("/track-by-client/{clientId}")
    @Operation(summary = "Track delivery by client id")
    public RoutOutput trackByClient(@PathVariable UUID clientId) {
        return deliveryService.getTrackingByClient(clientId);
    }

    @GetMapping("/delivery/{deliveryPersonId}")
    @Operation(summary = "rescue delivery id by delivery person id")
    public String getDeliveryId(@PathVariable UUID deliveryPersonId) {
        return deliveryService.getDeliveryId(deliveryPersonId);
    }
}
