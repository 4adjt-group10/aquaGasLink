package com.aquagaslink.delivery.controller;

import com.aquagaslink.delivery.controller.dto.DeliveryPersonForm;
import com.aquagaslink.delivery.model.DeliveryPerson;
import com.aquagaslink.delivery.service.DeliveryPersonService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/delivery-person")
public class DeliveryPersonController {

    private final DeliveryPersonService deliveryPersonService;

    public DeliveryPersonController(DeliveryPersonService deliveryPersonService) {
        this.deliveryPersonService = deliveryPersonService;
    }

    @PostMapping("/create")
    public DeliveryPerson create(@Valid @RequestBody DeliveryPersonForm deliveryPerson) {
        return deliveryPersonService.create(deliveryPerson);
    }
}
