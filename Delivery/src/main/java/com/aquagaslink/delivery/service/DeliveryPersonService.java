package com.aquagaslink.delivery.service;

import com.aquagaslink.delivery.controller.dto.DeliveryPersonDto;
import com.aquagaslink.delivery.controller.dto.DeliveryPersonForm;
import com.aquagaslink.delivery.infrastructure.DeliveryPersonRepository;
import com.aquagaslink.delivery.model.DeliveryPerson;
import com.aquagaslink.delivery.model.DeliveryPersonStatus;
import jakarta.persistence.EntityNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static com.aquagaslink.delivery.model.DeliveryPersonStatus.BUSY;

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

    /**
     * This method is scheduled to run every 2 minutes, every day.
     * {@code @Scheduled(cron = "0 0/2 * * * *")} configures this scheduling.
     */
    @Scheduled(cron = "0 0/2 * * * *")
    @Async
    public void updateStatusToAvailable() {
        deliveryPersonRepository.findFirstByStatus(BUSY).ifPresent(deliveryPerson -> {

        });
    }

    public Optional<DeliveryPerson> findFirstByStatus(DeliveryPersonStatus deliveryPersonStatus) {
        return deliveryPersonRepository.findFirstByStatus(deliveryPersonStatus);
    }

    public void save(@NotNull DeliveryPerson deliveryPerson) {
        deliveryPersonRepository.save(deliveryPerson);
    }

    public Optional<DeliveryPerson> findById(UUID id) {
        return deliveryPersonRepository.findById(id);
    }
}
