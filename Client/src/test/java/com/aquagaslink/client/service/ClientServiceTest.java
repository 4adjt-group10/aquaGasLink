package com.aquagaslink.client.service;
import static org.mockito.Mockito.*;

import com.aquagaslink.client.controller.clientDTO.ClientDTO;
import com.aquagaslink.client.queue.ClientEventGateway;
import com.aquagaslink.client.queue.dto.ClientToOrderOut;
import com.aquagaslink.client.queue.dto.OrderToClientIn;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.aquagaslink.client.infrastructure.ClientRepository;
import com.aquagaslink.client.model.ClientModel;
import com.aquagaslink.client.controller.clientDTO.ClientDTOForm;
import jakarta.persistence.EntityNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

class ClientServiceTest {


    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientEventGateway clientEventGateway;

    AutoCloseable openMocks;
    @BeforeEach
    void setup(){
        openMocks = MockitoAnnotations.openMocks(this);
    }
    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }
    @InjectMocks
    private ClientService clientService;

    @Test
    void testCreateClient_Success() {
        ClientDTOForm clientDTOForm = new ClientDTOForm(
                "12345678900", "John Doe", "john.doe@example.com", "1234567890",
                "12345", "123 Main St", "Springfield", "IL", "1", "USA");
        ClientModel clientModel = new ClientModel(clientDTOForm);
        when(clientRepository.findByCpf(clientDTOForm.cpf())).thenReturn(Optional.empty());
        when(clientRepository.save(any(ClientModel.class))).thenReturn(clientModel);

        ClientDTO result = clientService.createClient(clientDTOForm);

        assertNotNull(result);
        assertEquals(clientDTOForm.cpf(), result.cpf());
        assertEquals(clientDTOForm.name(), result.name());
        assertEquals(clientDTOForm.email(), result.email());
        assertEquals(clientDTOForm.phone(), result.phone());
        verify(clientRepository, times(1)).findByCpf(clientDTOForm.cpf());
        verify(clientRepository, times(1)).save(any(ClientModel.class));
    }

    @Test
    void testCreateClient_ClientAlreadyExists() {
        ClientDTOForm clientDTOForm = new ClientDTOForm(
                "12345678900", "John Doe", "john.doe@example.com", "1234567890",
                "12345", "123 Main St", "Springfield", "IL", "1", "USA");
        ClientModel existingClient = new ClientModel(clientDTOForm);
        when(clientRepository.findByCpf(clientDTOForm.cpf())).thenReturn(Optional.of(existingClient));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            clientService.createClient(clientDTOForm);
        });
        assertEquals("Client already exists", exception.getMessage());
        verify(clientRepository, times(1)).findByCpf(clientDTOForm.cpf());
        verify(clientRepository, never()).save(any(ClientModel.class));
    }

    @Test
    void testGetAllClient() {
        ClientModel client1 = new ClientModel(new ClientDTOForm(
                "12345678900", "John Doe", "john.doe@example.com", "1234567890",
                "12345", "123 Main St", "Springfield", "IL", "1", "USA"));
        ClientModel client2 = new ClientModel(new ClientDTOForm(
                "98765432100", "Jane Doe", "jane.doe@example.com", "0987654321",
                "54321", "456 Elm St", "Shelbyville", "IL", "2", "USA"));

        when(clientRepository.findAll()).thenReturn(Arrays.asList(client1, client2));

        List<ClientDTO> result = clientService.getAllClient();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(client1.getCpf(), result.get(0).cpf());
        assertEquals(client2.getCpf(), result.get(1).cpf());
        verify(clientRepository, times(1)).findAll();
    }

    @Test
    void testGetClient_Success() {
        UUID clientId = UUID.randomUUID();
        ClientModel clientModel = new ClientModel(new ClientDTOForm(
                "12345678900", "John Doe", "john.doe@example.com", "1234567890",
                "12345", "123 Main St", "Springfield", "IL", "1", "USA"));
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(clientModel));

        ClientDTO result = clientService.getClient(clientId);

        assertNotNull(result);
        assertEquals(clientModel.getCpf(), result.cpf());
        assertEquals(clientModel.getName(), result.name());
        assertEquals(clientModel.getEmail(), result.email());
        assertEquals(clientModel.getPhone(), result.phone());
        verify(clientRepository, times(1)).findById(clientId);
    }

    @Test
    void testGetClient_NotFound() {
        UUID clientId = UUID.randomUUID();
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        ClientDTO result = clientService.getClient(clientId);

        assertNull(result);
        verify(clientRepository, times(1)).findById(clientId);
    }

    @Test
    void testUpdateClient_Success() {
        UUID clientId = UUID.randomUUID();
        ClientDTOForm clientDTOForm = new ClientDTOForm(
                "12345678900", "John Doe Updated", "john.doe.updated@example.com", "1234567890",
                "12345", "123 Main St", "Springfield", "IL", "1", "USA");
        ClientModel existingClient = new ClientModel(new ClientDTOForm(
                "12345678900", "John Doe", "john.doe@example.com", "1234567890",
                "12345", "123 Main St", "Springfield", "IL", "1", "USA"));
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(existingClient));
        when(clientRepository.save(any(ClientModel.class))).thenReturn(existingClient);

        ClientDTO result = clientService.updateClient(clientId, clientDTOForm);

        assertNotNull(result);
        assertEquals(clientDTOForm.cpf(), result.cpf());
        assertEquals(clientDTOForm.name(), result.name());
        assertEquals(clientDTOForm.email(), result.email());
        assertEquals(clientDTOForm.phone(), result.phone());
        verify(clientRepository, times(1)).findById(clientId);
        verify(clientRepository, times(1)).save(any(ClientModel.class));
    }

    @Test
    void testUpdateClient_NotFound() {
        UUID clientId = UUID.randomUUID();
        ClientDTOForm clientDTOForm = new ClientDTOForm(
                "12345678900", "John Doe Updated", "john.doe.updated@example.com", "1234567890",
                "12345", "123 Main St", "Springfield", "IL", "1", "USA");
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            clientService.updateClient(clientId, clientDTOForm);
        });
        assertEquals("Client not found", exception.getMessage());
        verify(clientRepository, times(1)).findById(clientId);
        verify(clientRepository, never()).save(any(ClientModel.class));
    }

    @Test
    void testDeleteClient_NotFound() {
        UUID clientId = UUID.randomUUID();
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            clientService.deleteClient(clientId);
        });
        assertEquals("Client not found", exception.getMessage());
        verify(clientRepository, times(1)).findById(clientId);
        verify(clientRepository, never()).delete(any(ClientModel.class));
    }

    @Test
    void testValidateClientToOrder_ClientFound() {
        UUID clientId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();
        ClientModel clientModel = new ClientModel(new ClientDTOForm(
                "12345678900", "John Doe", "john.doe@example.com", "1234567890",
                "12345", "123 Main St", "Springfield", "IL", "1", "USA"));
        OrderToClientIn payload = new OrderToClientIn(clientId, orderId);

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(clientModel));

        clientService.validateClientToOrder(payload);

        verify(clientRepository, times(1)).findById(clientId);
        verify(clientEventGateway, times(1)).sendOrderEvent(any(ClientToOrderOut.class));
    }

    @Test
    void testValidateClientToOrder_ClientNotFound() {
        UUID clientId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();
        OrderToClientIn payload = new OrderToClientIn(clientId, orderId);

        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        clientService.validateClientToOrder(payload);

        verify(clientRepository, times(1)).findById(clientId);
        verify(clientEventGateway, times(1)).sendOrderEvent(any(ClientToOrderOut.class));
    }
}
