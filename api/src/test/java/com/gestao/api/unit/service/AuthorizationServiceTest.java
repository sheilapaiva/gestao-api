package com.gestao.api.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.gestao.api.entity.Admin;
import com.gestao.api.repository.AdminRepository;
import com.gestao.api.service.AuthorizationService;

public class AuthorizationServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AuthorizationService authorizationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserByUsernameSuccess() {
        String username = "adminUser";
        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword("password"); 

        when(adminRepository.findByUsername(username)).thenReturn(admin);

        UserDetails userDetails = authorizationService.loadUserByUsername(username);

        assertEquals(username, userDetails.getUsername());
    }

    @Test
    void testLoadUserByUsernameNotFound() {
        String username = "nonExistingUser";
        
        when(adminRepository.findByUsername(username)).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> {
            authorizationService.loadUserByUsername(username);
        });
    }

    @Test
    void testLoadUserByUsernameRepositoryException() {
        String username = "adminUser";
        
        when(adminRepository.findByUsername(username)).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> {
            authorizationService.loadUserByUsername(username);
        });
    }

    @Test
    void testLoadUserByUsernameValidUser() {
        String username = "adminUser";
        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword("password");

        when(adminRepository.findByUsername(username)).thenReturn(admin);

        UserDetails userDetails = authorizationService.loadUserByUsername(username);

        assertEquals(username, userDetails.getUsername());
    }

    @Test
    void testLoadUserByUsernameAdminConversion() {
        String username = "adminUser";
        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword("password");

        when(adminRepository.findByUsername(username)).thenReturn(admin);

        UserDetails userDetails = authorizationService.loadUserByUsername(username);

        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
    }
    
}
