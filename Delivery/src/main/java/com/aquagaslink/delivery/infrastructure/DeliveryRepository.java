package com.aquagaslink.delivery.infrastructure;

import com.aquagaslink.delivery.model.Delivery;
import com.aquagaslink.delivery.model.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, UUID> {
    Optional<Delivery> findByOrderId(UUID orderId);

    List<Delivery> findByStatus(DeliveryStatus deliveryStatus);

    @Query(value = """
        SELECT d FROM Delivery d
         WHERE jsonb_extract_path_text(d.deliveryClient, 'clientId') = :clientId
           AND d.status = :deliveryStatus
    """)
    Optional<Delivery> findByClientIdAndStatus(String clientId, DeliveryStatus deliveryStatus);
}
