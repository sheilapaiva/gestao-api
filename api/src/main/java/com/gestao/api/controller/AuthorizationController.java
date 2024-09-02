package com.gestao.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.authentication.AuthenticationManager;
import jakarta.validation.Valid;

import com.gestao.api.model.AuthenticationDTO;
import com.gestao.api.model.LoginResponseDTO;
import com.gestao.api.entity.Admin;
import com.gestao.api.exception.UnauthorizedException;
import com.gestao.api.config.TokenService;

@RestController
@RequestMapping("/admins")
public class AuthorizationController {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthorizationController.class);
    
    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {
        try{
            logger.info("Attempting login with username: {}", data.username());

            var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
            var auth = this.authenticationManager.authenticate(usernamePassword);
            var token = this.tokenService.generateToken((Admin) auth.getPrincipal());

            logger.info("Login successful for username: {}", data.username());
            return ResponseEntity.status(HttpStatus.OK).body(new LoginResponseDTO(token));

        } catch (Exception e) {
            logger.error("Login failed for username: {}", data.username(), e);
            throw new UnauthorizedException("Invalid credentials");
        }
    }

}
