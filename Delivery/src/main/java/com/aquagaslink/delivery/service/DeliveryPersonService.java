package com.aquagaslink.delivery.service;

import com.aquagaslink.delivery.controller.dto.DeliveryPersonForm;
import com.aquagaslink.delivery.infrastructure.DeliveryPersonRepository;
import com.aquagaslink.delivery.model.DeliveryPerson;
import org.springframework.stereotype.Service;

@Service
public class DeliveryPersonService {

    private final DeliveryPersonRepository deliveryPersonRepository;

    public DeliveryPersonService(DeliveryPersonRepository deliveryPersonRepository) {
        this.deliveryPersonRepository = deliveryPersonRepository;
    }


    public DeliveryPerson create(DeliveryPersonForm deliveryPerson) {
        DeliveryPerson newDeliveryPerson = deliveryPersonRepository.save(new DeliveryPerson(deliveryPerson));
        return ;
    }
}
