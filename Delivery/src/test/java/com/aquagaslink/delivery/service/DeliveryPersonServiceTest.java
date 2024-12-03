package com.aquagaslink.delivery.service;

import com.aquagaslink.delivery.controller.dto.DeliveryPersonDto;
import com.aquagaslink.delivery.controller.dto.DeliveryPersonForm;
import com.aquagaslink.delivery.infrastructure.DeliveryPersonRepository;
import com.aquagaslink.delivery.model.DeliveryPerson;
import com.aquagaslink.delivery.model.DeliveryPersonStatus;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeliveryPersonServiceTest {

    @Mock
    private DeliveryPersonRepository deliveryPersonRepository;

    @InjectMocks
    private DeliveryPersonService deliveryPersonService;

    private AutoCloseable mocks;

    @BeforeEach
    void setup() {
        mocks = MockitoAnnotations.openMocks(this);
        deliveryPersonService = new DeliveryPersonService(deliveryPersonRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

    @Test
    void testCreate() {

        DeliveryPersonForm form = new DeliveryPersonForm("name","email@email.com","321531356",DeliveryPersonStatus.AVAILABLE,"sadassd555");
        DeliveryPerson deliveryPerson = new DeliveryPerson(form);

        when(deliveryPersonRepository.save(any(DeliveryPerson.class))).thenReturn(deliveryPerson);

        DeliveryPersonDto result = deliveryPersonService.create(form);

        assertEquals(deliveryPerson.getId(), result.id());
        verify(deliveryPersonRepository, times(1)).save(any(DeliveryPerson.class));
    }

    @Test
    void testUpdate() {
        DeliveryPersonForm form = new DeliveryPersonForm("name","email@email.com","321531356",DeliveryPersonStatus.AVAILABLE,"sadassd555");
        DeliveryPerson deliveryPerson = new DeliveryPerson(form);

        when(deliveryPersonRepository.findByEmail(any(String.class))).thenReturn(Optional.of(deliveryPerson));
        when(deliveryPersonRepository.save(any(DeliveryPerson.class))).thenReturn(deliveryPerson);

        DeliveryPersonDto result = deliveryPersonService.update(form);

        assertEquals(deliveryPerson.getId(), result.id());
        verify(deliveryPersonRepository, times(1)).findByEmail(any(String.class));
        verify(deliveryPersonRepository, times(1)).save(any(DeliveryPerson.class));
    }

    @Test
    void testUpdateDeliveryPersonNotFound() {
        DeliveryPersonForm form = new DeliveryPersonForm("name","email@email.com","321531356",DeliveryPersonStatus.AVAILABLE,"sadassd555");

        when(deliveryPersonRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> deliveryPersonService.update(form));
    }

    @Test
    void testDelete() {
        UUID id = UUID.randomUUID();
        DeliveryPersonForm form = new DeliveryPersonForm("name","email@email.com","321531356",DeliveryPersonStatus.AVAILABLE,"sadassd555");
        DeliveryPerson deliveryPerson = new DeliveryPerson(form);


        when(deliveryPersonRepository.findById(any(UUID.class))).thenReturn(Optional.of(deliveryPerson));

        deliveryPersonService.delete(id);

        verify(deliveryPersonRepository, times(1)).findById(any(UUID.class));
        verify(deliveryPersonRepository, times(1)).delete(any(DeliveryPerson.class));
    }

    @Test
    void testDeleteDeliveryPersonNotFound() {
        UUID id = UUID.randomUUID();

        when(deliveryPersonRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> deliveryPersonService.delete(id));
    }

    @Test
    void testGetAll() {
        DeliveryPersonForm form = new DeliveryPersonForm("name","email@email.com","321531356",DeliveryPersonStatus.AVAILABLE,"sadassd555");
        DeliveryPerson deliveryPerson = new DeliveryPerson(form);
        Pageable pageable = PageRequest.of(0, 10);
        Page<DeliveryPerson> page = new PageImpl<>(Collections.singletonList(deliveryPerson));

        when(deliveryPersonRepository.findAll(pageable)).thenReturn(page);

        Page<DeliveryPersonDto> result = deliveryPersonService.getAll(pageable);

        assertEquals(1, result.getTotalElements());
        verify(deliveryPersonRepository, times(1)).findAll(pageable);
    }

    @Test
    void testGetAllByStatus() {
        DeliveryPersonForm form = new DeliveryPersonForm("name","email@email.com","321531356",DeliveryPersonStatus.AVAILABLE,"sadassd555");
        DeliveryPerson deliveryPerson = new DeliveryPerson(form);
        Pageable pageable = PageRequest.of(0, 10);
        Page<DeliveryPerson> page = new PageImpl<>(Collections.singletonList(deliveryPerson));

        when(deliveryPersonRepository.findAllByStatus(any(DeliveryPersonStatus.class), eq(pageable))).thenReturn(page);

        Page<DeliveryPersonDto> result = deliveryPersonService.getAllByStatus(DeliveryPersonStatus.BUSY, pageable);

        assertEquals(1, result.getTotalElements());
        verify(deliveryPersonRepository, times(1)).findAllByStatus(any(DeliveryPersonStatus.class), eq(pageable));
    }



    @Test
    void testGetDeliveryPersonByIdNotFound() {
        UUID id = UUID.randomUUID();

        when(deliveryPersonRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> deliveryPersonService.getDeliveryPersonById(id));
    }

    @Test
    void testUpdateStatusToAvailable() {
        DeliveryPersonForm form = new DeliveryPersonForm("name","email@email.com","321531356",DeliveryPersonStatus.AVAILABLE,"sadassd555");
        DeliveryPerson deliveryPerson = new DeliveryPerson(form);
        deliveryPerson.setStatus(DeliveryPersonStatus.BUSY);

        when(deliveryPersonRepository.findFirstByStatus(any(DeliveryPersonStatus.class))).thenReturn(Optional.of(deliveryPerson));

        deliveryPersonService.updateStatusToAvailable();

        verify(deliveryPersonRepository, times(1)).findFirstByStatus(any(DeliveryPersonStatus.class));
    }

    @Test
    void testFindFirstByStatus() {
        DeliveryPersonForm form = new DeliveryPersonForm("name","email@email.com","321531356",DeliveryPersonStatus.AVAILABLE,"sadassd555");
        DeliveryPerson deliveryPerson = new DeliveryPerson(form);

        when(deliveryPersonRepository.findFirstByStatus(any(DeliveryPersonStatus.class))).thenReturn(Optional.of(deliveryPerson));

        Optional<DeliveryPerson> result = deliveryPersonService.findFirstByStatus(DeliveryPersonStatus.BUSY);

        assertEquals(deliveryPerson, result.orElse(null));
        verify(deliveryPersonRepository, times(1)).findFirstByStatus(any(DeliveryPersonStatus.class));
    }

    @Test
    void testSave() {
        DeliveryPersonForm form = new DeliveryPersonForm("name","email@email.com","321531356",DeliveryPersonStatus.AVAILABLE,"sadassd555");
        DeliveryPerson deliveryPerson = new DeliveryPerson(form);

        deliveryPersonService.save(deliveryPerson);

        verify(deliveryPersonRepository, times(1)).save(any(DeliveryPerson.class));
    }

    @Test
    void testFindById() {
        UUID id = UUID.randomUUID();
        DeliveryPersonForm form = new DeliveryPersonForm("name","email@email.com","321531356",DeliveryPersonStatus.AVAILABLE,"sadassd555");
        DeliveryPerson deliveryPerson = new DeliveryPerson(form);
        when(deliveryPersonRepository.findById(any(UUID.class))).thenReturn(Optional.of(deliveryPerson));

        Optional<DeliveryPerson> result = deliveryPersonService.findById(id);

        assertEquals(deliveryPerson, result.orElse(null));
    }
}
