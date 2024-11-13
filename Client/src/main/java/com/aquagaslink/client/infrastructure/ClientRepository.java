package com.aquagaslink.client.infrastructure;

import ch.qos.logback.core.net.server.Client;
import com.aquagaslink.client.model.ClientModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<ClientModel, UUID> {
    Optional<Client> findByName(String name);

    Optional<Client> findByCpf(String cpf);
}
