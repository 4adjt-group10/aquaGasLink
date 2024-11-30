package com.aquagaslink.client.service;

import com.aquagaslink.client.controller.clientDTO.ClientDTO;
import com.aquagaslink.client.controller.clientDTO.ClientDTOForm;
import com.aquagaslink.client.infrastructure.ClientRepository;
import com.aquagaslink.client.model.ClientAddress;
import com.aquagaslink.client.model.ClientModel;
import com.aquagaslink.client.queue.ClientEventGateway;
import com.aquagaslink.client.queue.dto.ClientToOrderOut;
import com.aquagaslink.client.queue.dto.OrderToClientIn;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class ClientService {

    private Logger logger = Logger.getLogger(ClientService.class.getName());

    private final ClientRepository clientRepository;
    private final ClientEventGateway clientEventGateway;

    public ClientService(ClientRepository clientRepository, ClientEventGateway clientEventGateway) {
        this.clientRepository = clientRepository;
        this.clientEventGateway = clientEventGateway;
    }

    public ClientDTO createClient(ClientDTOForm clientDTOForm) {
        var client = clientRepository.findByCpf(clientDTOForm.cpf());
        if (client.isPresent()) {
            throw new EntityNotFoundException("Client already exists");
        }

        var clientModel = clientRepository.save(new ClientModel(clientDTOForm));
        return new ClientDTO(clientModel);
    }

    public List<ClientDTO> getAllClient() {
        return clientRepository.findAll().stream().map(ClientDTO::new).toList();
    }

    public ClientDTO getClient(UUID id) {
        var client = clientRepository.findById(id);
        return client.map(ClientDTO::new).orElse(null);
    }

    public ClientDTO updateClient(UUID id, ClientDTOForm clientDTOForm) {
    var client = clientRepository.findById(id);
    if (client.isEmpty()) {
        throw new EntityNotFoundException("Client not found");
    }
    var clientModel = client.get();
    clientModel.updateFromDTO(clientDTOForm);
    clientRepository.save(clientModel);

    return new ClientDTO(clientModel);
}

    public void deleteClient(UUID id) {
        var client = clientRepository.findById(id);
        if (client.isEmpty()) {
            throw new EntityNotFoundException("Client not found");
        }
        clientRepository.delete(client.get());

    }

    public void validateClientToOrder(OrderToClientIn payload) {
        Optional<ClientModel> client = clientRepository.findById(payload.clientId());
        ClientToOrderOut clientToOrderOut;
        if (client.isPresent()) {
            var clientModel = client.get();
            clientToOrderOut = new ClientToOrderOut(
                   clientModel.getId(),
                    payload.orderId(),
                    clientModel.getName(),
                    clientModel.getPhone(),
                    new ClientAddress(
                            clientModel.getPostalCode(),
                            clientModel.getAddress(),
                            clientModel.getCity(),
                            clientModel.getState(),
                            clientModel.getNumber(),
                            clientModel.getCountry()
                    ),
                    false,
                    ""
            );
            logger.info("Client found: " + clientModel.getName());
        } else {
            clientToOrderOut = new ClientToOrderOut(
                    payload.clientId(),
                    payload.orderId(),
                    "",
                    "",
                    new ClientAddress(
                            "",
                            "",
                            "",
                            "",
                            "",
                            ""
                    ),
                    true,
                    "Client not found"
            );
            logger.severe("Client not found: " + payload.clientId());
        }

        clientEventGateway.sendOrderEvent(clientToOrderOut);
    }
}
