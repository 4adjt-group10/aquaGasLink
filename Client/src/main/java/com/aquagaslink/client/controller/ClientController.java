package com.aquagaslink.client.controller;

import com.aquagaslink.client.controller.clientDTO.ClientDTO;
import com.aquagaslink.client.controller.clientDTO.ClientDTOForm;
import com.aquagaslink.client.queue.ClientEventGateway;
import com.aquagaslink.client.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    ClientEventGateway clientEventGateway;

    @PostMapping
    @Operation(summary = "Create new client")
    public ResponseEntity<ClientDTO> createClient(@RequestBody ClientDTOForm clientDTOForm) {
        ClientDTO createdClient = clientService.createClient(clientDTOForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdClient);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get client by id")
    public ResponseEntity<ClientDTO> getClient(@PathVariable UUID id) {
        ClientDTO client = clientService.getClient(id);
        return ResponseEntity.ok(client);
    }

    @GetMapping("/all")
    @Operation(summary = "List all clients")
    public ResponseEntity<List<ClientDTO>> getAllClient() {
        List<ClientDTO> client = clientService.getAllClient();
        return ResponseEntity.ok(client);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update client by id")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable UUID id, @RequestBody ClientDTOForm clientDTOForm) {
        ClientDTO updatedClient = clientService.updateClient(id, clientDTOForm);
        return ResponseEntity.ok(updatedClient);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete client by id")
    public ResponseEntity<Void> deleteClient(@PathVariable UUID id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
