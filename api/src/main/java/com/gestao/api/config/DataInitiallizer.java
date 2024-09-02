package com.gestao.api.config;

import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.gestao.api.repository.AdminRepository;
import com.gestao.api.entity.Admin;

@Component
public class DataInitiallizer {
    
    @Bean
    public CommandLineRunner init(AdminRepository adminRepository) {
        return args -> {
            if (adminRepository.count() == 0) {
                
                String encryptedPassword = new BCryptPasswordEncoder().encode("admin");
                Admin admin = new Admin("admin", encryptedPassword);
                adminRepository.save(admin);
            }
        };
    }   
}
