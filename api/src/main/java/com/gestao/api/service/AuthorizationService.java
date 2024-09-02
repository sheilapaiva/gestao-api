package com.gestao.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.gestao.api.repository.AdminRepository;

import jakarta.transaction.Transactional;

@Service
public class AuthorizationService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(AuthorizationService.class);

    @Autowired
    private AdminRepository adminRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Searching for user with username {}", username);
        UserDetails userDetails = adminRepository.findByUsername(username);
        
        if (userDetails == null) {
            logger.error("User with username {} not found.", username);
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return userDetails;
    }
}
