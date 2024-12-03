package com.aquagaslink.client.controller;

import com.aquagaslink.client.controller.clientDTO.ClientDTO;
import com.aquagaslink.client.controller.clientDTO.ClientDTOForm;
import com.aquagaslink.client.helper.ClientHelper;
import com.aquagaslink.client.service.ClientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientControllerTest {
    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    @Autowired
    private ClientHelper clientHelper;


    @Test
    public void testCreateClient() {

        // Given
        ClientDTOForm clientDTOForm = clientHelper.createClient("1234557");
        ClientDTO createdClient = clientHelper.createClientDto("1234557");
        when(clientService.createClient(clientDTOForm)).thenReturn(createdClient);

        // When
        ResponseEntity<ClientDTO> response = clientController.createClient(clientDTOForm);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdClient, response.getBody());
    }

    @Test
    public void testGetClient() {
        // Given
        UUID id = UUID.randomUUID();
        ClientDTO clientDTO = clientHelper.createClientDto("1234557");
        when(clientService.getClient(id)).thenReturn(clientDTO);

        // When
        ResponseEntity<ClientDTO> response = clientController.getClient(id);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clientDTO, response.getBody());
    }

    @Test
    public void testGetAllClients() {
        // Given
        List<ClientDTO> clients = Arrays.asList(clientHelper.createClientDto("1234557"), clientHelper.createClientDto("1234552"));
        when(clientService.getAllClient()).thenReturn(clients);

        // When
        ResponseEntity<List<ClientDTO>> response = clientController.getAllClient();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clients, response.getBody());
    }

    @Test
    public void testDeleteClient() {
        // Given
        UUID id = UUID.randomUUID();

        // When
        ResponseEntity<Void> response = clientController.deleteClient(id);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(clientService).deleteClient(id);
    }

    @Test
    public void testUpdateClient() {
        // Given
        UUID id = UUID.randomUUID();
        ClientDTOForm clientDTOForm = clientHelper.createClient("1234557");
        ClientDTO updatedClient = clientHelper.createClientDto("1234557");
        when(clientService.updateClient(id, clientDTOForm)).thenReturn(updatedClient);

        // When
        ResponseEntity<ClientDTO> response = clientController.updateClient(id, clientDTOForm);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedClient, response.getBody());
    }
}
