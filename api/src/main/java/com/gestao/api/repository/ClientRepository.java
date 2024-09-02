package com.gestao.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gestao.api.entity.Client;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> { 
    
    Optional<Client> findByCnpj(String cnpj);
    Optional<Client> findByEmail(String email);
    
} 
