package com.gestao.api.model;

import org.hibernate.validator.constraints.br.CNPJ;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ClientDTO {
        
    private Long id;

    @NotBlank(message = "Name is required.")
    private String name;

    @NotBlank(message = "Email is required.")
    @Email(message = "The email must be valid.")
    private String email;

    @NotBlank(message = "Phone is required.")
    private String phone;

    @NotBlank(message = "CNPJ is required.")
    @CNPJ(message = "The CNPJ must be valid.")
    private String cnpj;

    @NotBlank(message = "Address is required.")
    private String address;
    
    public ClientDTO() {
    }
    
    public ClientDTO(Long id, String name, String email, String phone, String cnpj, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.cnpj = cnpj;
        this.address = address;
    }
    
    public Long getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public String getCnpj() {
        return cnpj;
    }
    
    public String getAddress() {
        return address;
    }
}
