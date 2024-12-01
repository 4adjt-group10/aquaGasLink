package com.aquagaslink.delivery.service;

import com.aquagaslink.delivery.controller.dto.DeliveryPersonDto;
import com.aquagaslink.delivery.controller.dto.DeliveryPersonForm;
import com.aquagaslink.delivery.infrastructure.DeliveryPersonRepository;
import com.aquagaslink.delivery.model.DeliveryPerson;
import com.aquagaslink.delivery.model.DeliveryPersonStatus;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.aquagaslink.delivery.model.DeliveryPersonStatus.AVAILABLE;

@Service
public class DeliveryPersonService {

    private final DeliveryPersonRepository deliveryPersonRepository;

    public DeliveryPersonService(DeliveryPersonRepository deliveryPersonRepository) {
        this.deliveryPersonRepository = deliveryPersonRepository;
    }

    public DeliveryPersonDto create(DeliveryPersonForm deliveryPerson) {
        DeliveryPerson newDeliveryPerson = deliveryPersonRepository.save(new DeliveryPerson(deliveryPerson));
        return new DeliveryPersonDto(newDeliveryPerson);
    }

    public DeliveryPersonDto update(DeliveryPersonForm deliveryPersonForm) {
        DeliveryPerson deliveryPerson = deliveryPersonRepository.findByEmail(deliveryPersonForm.email())
                .orElseThrow(() -> new IllegalArgumentException("Delivery person not found"));
        deliveryPerson.merge(deliveryPersonForm);
        deliveryPersonRepository.save(deliveryPerson);
        return new DeliveryPersonDto(deliveryPerson);
    }

    public void delete(UUID id) {
        DeliveryPerson deliveryPerson = deliveryPersonRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Delivery person not found"));
        deliveryPersonRepository.delete(deliveryPerson);
    }

    public Page<DeliveryPersonDto> getAll(Pageable pageable) {
        return deliveryPersonRepository.findAll(pageable).map(DeliveryPersonDto::new);
    }

    public Page<DeliveryPersonDto> getAllByStatus(DeliveryPersonStatus status, Pageable pageable) {
        return deliveryPersonRepository.findAllByStatus(status, pageable).map(DeliveryPersonDto::new);
    }

    public DeliveryPersonDto getDeliveryPersonById(UUID id) {
        return deliveryPersonRepository.findById(id)
                .map(DeliveryPersonDto::new)
                .orElseThrow(() -> new EntityNotFoundException("Delivery person not found"));
    }

    public DeliveryPerson getAvailableDeliveryPerson() {
        return deliveryPersonRepository.findFirstByStatus(AVAILABLE)
                .orElseThrow(() -> new EntityNotFoundException("No available delivery person"));
    }
}
