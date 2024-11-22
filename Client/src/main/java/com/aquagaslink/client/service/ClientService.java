package com.aquagaslink.client.service;

import com.aquagaslink.client.controller.clientDTO.ClientDTO;
import com.aquagaslink.client.controller.clientDTO.ClientDTOForm;
import com.aquagaslink.client.infrastructure.ClientRepository;
import com.aquagaslink.client.model.ClientModel;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;


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
}
