package com.aquagaslink.delivery.infrastructure;

import com.aquagaslink.delivery.model.DeliveryPerson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryPersonRepository extends JpaRepository<DeliveryPerson, Long> {
}
