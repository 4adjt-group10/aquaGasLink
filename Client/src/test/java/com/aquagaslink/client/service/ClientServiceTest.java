//package com.aquagaslink.client.service;
//import static org.mockito.Mockito.*;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import com.aquagaslink.client.infrastructure.ClientRepository;
//import com.aquagaslink.client.model.ClientModel;
//import com.aquagaslink.client.controller.clientDTO.ClientDTOForm;
//import jakarta.persistence.EntityNotFoundException;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//class ClientServiceTest {
//
//
//    @Mock
//    private ClientRepository clientRepository;
//
//    AutoCloseable openMocks;
//    @BeforeEach
//    void setup(){
//        openMocks = MockitoAnnotations.openMocks(this);
//    }
//    @AfterEach
//    void tearDown() throws Exception {
//        openMocks.close();
//    }
//    @InjectMocks
//    private ClientService clientService;
//
//    @Test
//    void createClient() {
//
//        ClientDTOForm clientDTOForm = new ClientDTOForm("12345678900", "John Doe", "john@gmail.com", "249876765", "teste address", "teste city", " teste state", "teste number", "teste country");
//        ClientModel clientModel = new ClientModel(clientDTOForm);
//
//        when(clientRepository.findByCpf(clientDTOForm.cpf())).thenReturn(Optional.empty());
//        when(clientRepository.save(any(ClientModel.class))).thenReturn(clientModel);
//
//        var result = clientService.createClient(clientDTOForm);
//
//        assertNotNull(result);
//        assertEquals(clientDTOForm.cpf(), result.cpf());
//        verify(clientRepository, times(1)).findByCpf(clientDTOForm.cpf());
//        verify(clientRepository, times(1)).save(any(ClientModel.class));
//
//    }
//
//    @Test
//    void createClient_ClientAlreadyExists_ThrowsException() {
//        ClientDTOForm clientDTOForm = new ClientDTOForm("12345678900", "John Doe", "john@gmail.com", "249876765", "teste address", "teste city", "teste state", "teste number", "teste country");
//        ClientModel clientModel = new ClientModel(clientDTOForm);
//
//        when(clientRepository.findByCpf(clientDTOForm.cpf())).thenReturn(Optional.of(clientModel));
//
//        assertThrows(EntityNotFoundException.class, () -> clientService.createClient(clientDTOForm));
//
//        verify(clientRepository, times(1)).findByCpf(clientDTOForm.cpf());
//        verify(clientRepository, never()).save(any(ClientModel.class));
//    }
//
//    @Test
//    void getAllClient() {
//        ClientModel client1 = new ClientModel(new ClientDTOForm("12345678900", "John Doe", "john@gmail.com", "249876765", "teste address", "teste city", "teste state", "teste number", "teste country"));
//        ClientModel client2 = new ClientModel(new ClientDTOForm("09876543211", "Jane Doe", "jane@gmail.com", "249876766", "teste address 2", "teste city 2", "teste state 2", "teste number 2", "teste country 2"));
//
//        when(clientRepository.findAll()).thenReturn(List.of(client1, client2));
//
//        var result = clientService.getAllClient();
//
//        assertNotNull(result);
//        assertEquals(2, result.size());
//        assertEquals(client1.getCpf(), result.get(0).cpf());
//        assertEquals(client2.getCpf(), result.get(1).cpf());
//        verify(clientRepository, times(1)).findAll();
//    }
//
//    @Test
//    void getClient() {
//        UUID clientId = UUID.randomUUID();
//        ClientModel clientModel = new ClientModel(new ClientDTOForm("12345678900", "John Doe", "john@gmail.com", "249876765", "teste address", "teste city", "teste state", "teste number", "teste country"));
//
//        when(clientRepository.findById(clientId)).thenReturn(Optional.of(clientModel));
//
//        var result = clientService.getClient(clientId);
//
//        assertNotNull(result);
//        assertEquals(clientModel.getCpf(), result.cpf());
//        verify(clientRepository, times(1)).findById(clientId);
//    }
//
//    @Test
//    void getClient_ReturnsNull_WhenClientNotFound() {
//        UUID clientId = UUID.randomUUID();
//
//        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());
//
//        var result = clientService.getClient(clientId);
//
//        assertNull(result);
//        verify(clientRepository, times(1)).findById(clientId);
//    }
//
//
//    @Test
//    void updateClient() {
//        UUID clientId = UUID.randomUUID();
//        ClientDTOForm clientDTOForm = new ClientDTOForm("12345678900", "John Doe", "john@gmail.com", "249876765", "teste address", "teste city", "teste state", "teste number", "teste country");
//        ClientModel existingClient = new ClientModel(clientDTOForm);
//
//        when(clientRepository.findById(clientId)).thenReturn(Optional.of(existingClient));
//        when(clientRepository.save(any(ClientModel.class))).thenReturn(existingClient);
//
//        var result = clientService.updateClient(clientId, clientDTOForm);
//
//        assertNotNull(result);
//        assertEquals(clientDTOForm.cpf(), result.cpf());
//        assertEquals(clientDTOForm.name(), result.name());
//        assertEquals(clientDTOForm.email(), result.email());
//        assertEquals(clientDTOForm.phone(), result.phone());
//        assertEquals(clientDTOForm.address(), result.address());
//        assertEquals(clientDTOForm.city(), result.city());
//        assertEquals(clientDTOForm.state(), result.state());
//        assertEquals(clientDTOForm.number(), result.number());
//        assertEquals(clientDTOForm.country(), result.country());
//        verify(clientRepository, times(1)).findById(clientId);
//        verify(clientRepository, times(1)).save(any(ClientModel.class));
//    }
//
//    @Test
//    void updateClient_ClientNotFound_ThrowsException() {
//        UUID clientId = UUID.randomUUID();
//        ClientDTOForm clientDTOForm = new ClientDTOForm("12345678900", "John Doe", "john@gmail.com", "249876765", "teste address", "teste city", "teste state", "teste number", "teste country");
//
//        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());
//
//        assertThrows(EntityNotFoundException.class, () -> clientService.updateClient(clientId, clientDTOForm));
//
//        verify(clientRepository, times(1)).findById(clientId);
//        verify(clientRepository, never()).save(any(ClientModel.class));
//    }
//
//
//    @Test
//    void deleteClient() {
//        UUID clientId = UUID.randomUUID();
//        ClientModel clientModel = new ClientModel(new ClientDTOForm("12345678900", "John Doe", "john@gmail.com", "249876765", "teste address", "teste city", "teste state", "teste number", "teste country"));
//
//        when(clientRepository.findById(clientId)).thenReturn(Optional.of(clientModel));
//
//        clientService.deleteClient(clientId);
//
//        verify(clientRepository, times(1)).findById(clientId);
//        verify(clientRepository, times(1)).delete(clientModel);
//    }
//
//    @Test
//    void deleteClient_ClientNotFound_ThrowsException() {
//        UUID clientId = UUID.randomUUID();
//
//        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());
//
//        assertThrows(EntityNotFoundException.class, () -> clientService.deleteClient(clientId));
//
//        verify(clientRepository, times(1)).findById(clientId);
//        verify(clientRepository, never()).delete(any(ClientModel.class));
//    }
//}