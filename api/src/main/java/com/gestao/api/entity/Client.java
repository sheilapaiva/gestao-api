package com.gestao.api.entity;

import org.hibernate.validator.constraints.br.CNPJ;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "table_clients")
public class Client {
    
    @Id
    @Column(unique = true, nullable = false, length = 20)
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Name is required.")
    private String name;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Email is required.")
    @Email(message = "The email must be valid.")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "Phone is required.")
    private String phone;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "CNPJ is required.")
    @CNPJ(message = "The CNPJ must be valid.")
    private String cnpj;

    @Column(nullable = false)
    @NotBlank(message = "Address is required.")
    private String address;

    public Client() {
    }

    public Client(String name, String email, String phone, String cnpj, String address) {
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

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Client [address=" + address + ", CNPJ=" + cnpj + ", email=" + email + ", id=" + id + ", name=" + name
                + ", phone=" + phone + "]";
    }
}
