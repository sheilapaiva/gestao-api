package com.gestao.api.unit.entity;

import org.junit.jupiter.api.Test;

import com.gestao.api.entity.Client;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidatorFactory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import java.util.Set;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

public class ClientTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidClient() {
        Client client = new Client("John Doe", "john.doe@example.com", "123456789", "12.345.678/0001-95", "123 Main St");

        Set<ConstraintViolation<Client>> violations = validator.validate(client);

        Assertions.assertTrue(violations.isEmpty(), "No violations should be present for a valid client.");
    }

    @Test
    void testBlankName() {
        Client client = new Client("", "john.doe@example.com", "123456789", "12.345.678/0001-95", "123 Main St");

        Set<ConstraintViolation<Client>> violations = validator.validate(client);

        boolean hasNameViolation = violations.stream()
            .anyMatch(v -> "name".equals(v.getPropertyPath().toString()) && "Name is required.".equals(v.getMessage()));

        Assertions.assertTrue(hasNameViolation, "Expected validation error for blank name.");
    }

    @Test
    void testNullName() {
        Client client = new Client(null, "john.doe@example.com", "123456789", "12.345.678/0001-95", "123 Main St");

        Set<ConstraintViolation<Client>> violations = validator.validate(client);

        boolean hasNameViolation = violations.stream()
            .anyMatch(v -> "name".equals(v.getPropertyPath().toString()) && "Name is required.".equals(v.getMessage()));

        Assertions.assertTrue(hasNameViolation, "Expected validation error for null name.");
    }

    @Test
    void testInvalidEmail() {
        Client client = new Client("John Doe", "invalid-email", "123456789", "12.345.678/0001-95", "123 Main St");

        Set<ConstraintViolation<Client>> violations = validator.validate(client);

        boolean hasEmailViolation = violations.stream()
            .anyMatch(v -> "email".equals(v.getPropertyPath().toString()) && "The email must be valid.".equals(v.getMessage()));

        Assertions.assertTrue(hasEmailViolation, "Expected validation error for invalid email.");
    }

    @Test
    void testBlankPhone() {
        Client client = new Client("John Doe", "john.doe@example.com", "", "12.345.678/0001-95", "123 Main St");

        Set<ConstraintViolation<Client>> violations = validator.validate(client);

        boolean hasPhoneViolation = violations.stream()
            .anyMatch(v -> "phone".equals(v.getPropertyPath().toString()) && "Phone is required.".equals(v.getMessage()));

        Assertions.assertTrue(hasPhoneViolation, "Expected validation error for blank phone.");
    }

    @Test
    void testNullPhone() {
        Client client = new Client("John Doe", "john.doe@example.com", null, "12.345.678/0001-95", "123 Main St");

        Set<ConstraintViolation<Client>> violations = validator.validate(client);

        boolean hasPhoneViolation = violations.stream()
            .anyMatch(v -> "phone".equals(v.getPropertyPath().toString()) && "Phone is required.".equals(v.getMessage()));

        Assertions.assertTrue(hasPhoneViolation, "Expected validation error for null phone.");
    }

    @Test
    void testInvalidCnpj() {
        Client client = new Client("John Doe", "john.doe@example.com", "123456789", "12.345.678/0001-98", "123 Main St");

        Set<ConstraintViolation<Client>> violations = validator.validate(client);

        boolean hasCnpjViolation = violations.stream()
            .anyMatch(v -> "cnpj".equals(v.getPropertyPath().toString()) && "The CNPJ must be valid.".equals(v.getMessage()));

        Assertions.assertTrue(hasCnpjViolation, "Expected validation error for invalid CNPJ.");
    }

    @Test
    void testBlankAddress() {
        Client client = new Client("John Doe", "john.doe@example.com", "123456789", "12.345.678/0001-95", "");

        Set<ConstraintViolation<Client>> violations = validator.validate(client);

        boolean hasAddressViolation = violations.stream()
            .anyMatch(v -> "address".equals(v.getPropertyPath().toString()) && "Address is required.".equals(v.getMessage()));

        Assertions.assertTrue(hasAddressViolation, "Expected validation error for blank address.");
    }

    @Test
    void testNullAddress() {
        Client client = new Client("John Doe", "john.doe@example.com", "123456789", "12.345.678/0001-95", null);

        Set<ConstraintViolation<Client>> violations = validator.validate(client);

        boolean hasAddressViolation = violations.stream()
            .anyMatch(v -> "address".equals(v.getPropertyPath().toString()) && "Address is required.".equals(v.getMessage()));

        Assertions.assertTrue(hasAddressViolation, "Expected validation error for null address.");
    }

    @Test
    void testGettersAndSetters() {
        Client client = new Client();
        client.setName("Jane Doe");
        client.setEmail("jane.doe@example.com");
        client.setPhone("987654321");
        client.setCnpj("98.765.432/0001-98");
        client.setAddress("456 Elm St");

        Assertions.assertEquals("Jane Doe", client.getName());
        Assertions.assertEquals("jane.doe@example.com", client.getEmail());
        Assertions.assertEquals("987654321", client.getPhone());
        Assertions.assertEquals("98.765.432/0001-98", client.getCnpj());
        Assertions.assertEquals("456 Elm St", client.getAddress());
    }
}
