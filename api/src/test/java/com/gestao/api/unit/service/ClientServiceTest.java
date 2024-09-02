package com.gestao.api.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.gestao.api.entity.Client;
import com.gestao.api.exception.AlreadyExistsException;
import com.gestao.api.exception.NotFoundException;
import com.gestao.api.model.ClientDTO;
import com.gestao.api.repository.ClientRepository;
import com.gestao.api.service.ClientService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateClient() {
        ClientDTO dto = new ClientDTO(null, "John Doe", "john.doe@example.com", "123456789", "12.345.678/0001-95", "123 Main St");
        Client client = new Client("John Doe", "john.doe@example.com", "123456789", "12.345.678/0001-95", "123 Main St");

        when(clientRepository.findByCnpj("12.345.678/0001-95")).thenReturn(Optional.empty());
        when(clientRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.empty());
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        ClientDTO createdClient = clientService.createClient(dto);

        assertNotNull(createdClient);
        assertEquals("John Doe", createdClient.getName());
        verify(clientRepository, times(1)).save(any(Client.class));
    }

    @Test
    void testCreateClientCnpjAlreadyExists() {
        ClientDTO dto = new ClientDTO(null, "John Doe", "john.doe@example.com", "123456789", "12.345.678/0001-95", "123 Main St");

        when(clientRepository.findByCnpj("12.345.678/0001-95")).thenReturn(Optional.of(new Client()));

        Exception exception = assertThrows(AlreadyExistsException.class, () -> {
            clientService.createClient(dto);
        });

        assertEquals("There is already a registered Client with the CNPJ provided.", exception.getMessage());
    }

    @Test
    void testCreateClientEmailAlreadyExists() {
        ClientDTO dto = new ClientDTO(null, "John Doe", "john.doe@example.com", "123456789", "12.345.678/0001-95", "123 Main St");

        when(clientRepository.findByCnpj("12.345.678/0001-95")).thenReturn(Optional.empty());
        when(clientRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(new Client()));

        Exception exception = assertThrows(AlreadyExistsException.class, () -> {
            clientService.createClient(dto);
        });

        assertEquals("There is already a registered Client with the e-mail address provided.", exception.getMessage());
    }

    @Test
    void testFindClientById() {
        Client client = new Client("John Doe", "john.doe@example.com", "123456789", "12.345.678/0001-95", "123 Main St");
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        ClientDTO dto = clientService.findClientById(1L);

        assertNotNull(dto);
        assertEquals("John Doe", dto.getName());
    }

    @Test
    void testFindClientByIdNotFound() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class, () -> {
            clientService.findClientById(1L);
        });

        assertEquals("Client with ID 1 not found.", exception.getMessage());
    }

    @Test
    void testFindAllClients() {
        Client client1 = new Client("John Doe", "john.doe@example.com", "123456789", "12.345.678/0001-95", "123 Main St");
        Client client2 = new Client("Jane Doe", "jane.doe@example.com", "987654321", "98.765.432/0001-98", "456 Elm St");

        when(clientRepository.findAll()).thenReturn(Arrays.asList(client1, client2));

        List<ClientDTO> clients = clientService.findAllClients();

        assertNotNull(clients);
        assertEquals(2, clients.size());
        assertEquals("John Doe", clients.get(0).getName());
        assertEquals("Jane Doe", clients.get(1).getName());
    }

    @Test
    void testUpdateClient() {
        ClientDTO dto = new ClientDTO(null, "John Doe", "john.doe@example.com", "123456789", "12.345.678/0001-95", "123 Main St");
        Client existingClient = new Client("John Doe", "john.doe@example.com", "123456789", "12.345.678/0001-95", "123 Main St");

        when(clientRepository.findById(1L)).thenReturn(Optional.of(existingClient));
        when(clientRepository.save(any(Client.class))).thenReturn(existingClient);

        ClientDTO updatedClient = clientService.updateClient(1L, dto);

        assertNotNull(updatedClient);
        assertEquals("John Doe", updatedClient.getName());
        verify(clientRepository, times(1)).save(any(Client.class));
    }

    @Test
    void testUpdateClientNotFound() {
        ClientDTO dto = new ClientDTO(null, "John Doe", "john.doe@example.com", "123456789", "12.345.678/0001-95", "123 Main St");

        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class, () -> {
            clientService.updateClient(1L, dto);
        });

        assertEquals("Client with ID 1 not found.", exception.getMessage());
    }

    @Test
    void testDeleteClient() {
        when(clientRepository.existsById(1L)).thenReturn(true);

        clientService.deleteClient(1L);

        verify(clientRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteClientNotFound() {
        when(clientRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(NotFoundException.class, () -> {
            clientService.deleteClient(1L);
        });

        assertEquals("Client with ID 1 not found.", exception.getMessage());
    }
}
