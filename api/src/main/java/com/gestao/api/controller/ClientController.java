package com.gestao.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import com.gestao.api.service.ClientService;
import com.gestao.api.model.ClientDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/clients")
public class ClientController {
    
    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientDTO> createClient(@RequestBody @Valid ClientDTO client) {
        logger.info("Creating new client: {}", client);
        ClientDTO createdClient = clientService.createClient(client);
        logger.info("Client created successfully: {}", createdClient);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdClient);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> findClientById(@PathVariable Long id) {
        logger.info("Fetching client with ID: {}", id);
        ClientDTO client = clientService.findClientById(id);
        logger.info("Client retrieved: {}", client);
        return ResponseEntity.ok(client);    
    }

    @GetMapping
    public ResponseEntity<List<ClientDTO>> findAllClients() {
        logger.info("Fetching all clients");
        List<ClientDTO> clients = clientService.findAllClients();
        logger.info("Total clients retrieved: {}", clients.size());
        return ResponseEntity.ok(clients);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable Long id, @Valid @RequestBody ClientDTO client) {
        logger.info("Updating client with ID: {}. New data: {}", id, client);
        ClientDTO updatedClient = clientService.updateClient(id, client);
        logger.info("Client updated successfully: {}", updatedClient);
        return ResponseEntity.ok(updatedClient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        logger.info("Deleting client with ID: {}", id);
        clientService.deleteClient(id);
        logger.info("Client deleted successfully");
        return ResponseEntity.noContent().build();
    }

}
