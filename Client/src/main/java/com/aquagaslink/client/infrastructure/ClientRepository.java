package com.aquagaslink.client.infrastructure;

import com.aquagaslink.client.model.ClientModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<ClientModel, UUID> {
    Optional<ClientModel> findByName(String name);

    Optional<ClientModel> findByCpf(String cpf);
}
