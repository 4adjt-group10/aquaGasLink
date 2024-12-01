package com.aquagaslink.delivery.infrastructure;

import com.aquagaslink.delivery.model.DeliveryPerson;
import com.aquagaslink.delivery.model.DeliveryPersonStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DeliveryPersonRepository extends JpaRepository<DeliveryPerson, UUID> {

    Optional<DeliveryPerson> findByEmail(String email);

    Page<DeliveryPerson> findAllByStatus(DeliveryPersonStatus status, Pageable pageable);

    Optional<DeliveryPerson> findFirstByStatus(DeliveryPersonStatus status);
}
