package com.gestao.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestao.api.repository.ClientRepository;
import com.gestao.api.exception.NotFoundException;
import com.gestao.api.model.ClientDTO;
import com.gestao.api.entity.Client;
import com.gestao.api.exception.AlreadyExistsException;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);
    
    @Autowired
    private ClientRepository clientRepository;

    @Transactional
    public ClientDTO createClient(ClientDTO client) {
        logger.info("Starting creation of client: {}", client);

        if (clientRepository.findByCnpj(client.getCnpj()).isPresent()) {
            logger.error("Client with CNPJ {} is already registered.", client.getCnpj());
            throw new AlreadyExistsException("There is already a registered Client with the CNPJ provided.");
        }
        if (clientRepository.findByEmail(client.getEmail()).isPresent()) {
            logger.error("Client with email {} is already registered.", client.getEmail());
            throw new AlreadyExistsException("There is already a registered Client with the e-mail address provided.");
        }

        Client newclient = new Client(client.getName(), client.getEmail(), client.getPhone(), client.getCnpj(), client.getAddress());
        Client savedClient = clientRepository.save(newclient);
        logger.info("Client created successfully: {}", savedClient);

        return new ClientDTO(savedClient.getId(), savedClient.getName(), savedClient.getEmail(), savedClient.getPhone(), savedClient.getCnpj(), savedClient.getAddress());
    }

    @Transactional
    public ClientDTO findClientById(Long id) {
        logger.info("Searching for client with ID {}", id);

        Client client = clientRepository.findById(id).orElse(null);

        if (client == null) {
            logger.error("Client with ID {} not found.", id);
            throw new NotFoundException("Client with ID " + id + " not found.");
        }

        return new ClientDTO(client.getId(), client.getName(), client.getEmail(), client.getPhone(), client.getCnpj(), client.getAddress());
    }

    @Transactional
    public List<ClientDTO> findAllClients() {
        logger.info("Fetching all clients");

        return clientRepository.findAll().stream()
                .map(client -> new ClientDTO(client.getId(), client.getName(), client.getEmail(), client.getPhone(), client.getCnpj(), client.getAddress()))
                .collect(Collectors.toList());
    }

    @Transactional
    public ClientDTO updateClient(Long id, ClientDTO client) {
        logger.info("Updating client with ID {}", id);

        Client clientExistente = clientRepository.findById(id).orElse(null);

        if (clientExistente == null) {
            logger.error("Client with ID {} not found for update.", id);
            throw new NotFoundException("Client with ID " + id + " not found.");
        }
        
        clientExistente.setName(client.getName());
        clientExistente.setEmail(client.getEmail());
        clientExistente.setPhone(client.getPhone());
        clientExistente.setCnpj(client.getCnpj());
        clientExistente.setAddress(client.getAddress());

        Client updatedClient = clientRepository.save(clientExistente);
        logger.info("Client updated successfully: {}", updatedClient);

        return new ClientDTO(updatedClient.getId(), updatedClient.getName(), updatedClient.getEmail(), updatedClient.getPhone(), updatedClient.getCnpj(), updatedClient.getAddress());
    }

    @Transactional
    public void deleteClient(Long id) {
        logger.info("Deleting client with ID {}", id);

        if (!clientRepository.existsById(id)) {
            logger.error("Client with ID {} not found for deletion.", id);
            throw new NotFoundException("Client with ID " + id + " not found.");
        }

        clientRepository.deleteById(id);
        logger.info("Client with ID {} deleted successfully.", id);
    }
}
