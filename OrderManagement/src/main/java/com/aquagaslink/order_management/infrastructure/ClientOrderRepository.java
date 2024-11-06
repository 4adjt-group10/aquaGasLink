package com.aquagaslink.order_management.infrastructure;

import com.aquagaslink.order_management.model.ClientOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientOrderRepository extends JpaRepository<ClientOrder, UUID> {

    Page<ClientOrder> findAllByClientId(UUID clientId, Pageable pageable);
}
