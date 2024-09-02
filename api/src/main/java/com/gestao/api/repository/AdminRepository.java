package com.gestao.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.gestao.api.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    
    UserDetails findByUsername(String username);

}
