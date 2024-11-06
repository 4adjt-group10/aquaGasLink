package com.aquagaslink.order_management.infrastructure;

import com.aquagaslink.order_management.model.ClientOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ClientOrderRepository extends JpaRepository<ClientOrder, UUID> {

    List<ClientOrder> findAllByClientId(UUID clientId);
}
