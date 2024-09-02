package com.gestao.api.unit.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gestao.api.entity.Vehicle;
import com.gestao.api.exception.InvalidException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;

public class VehicleTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidYear() {
        Vehicle vehicle = new Vehicle("ABC-1234", "Ford", "Mustang", "Red", "2024");

        Assertions.assertEquals("2024", vehicle.getYear());
    }

    @Test
    void testInvalidYear() {
        Exception exception = Assertions.assertThrows(InvalidException.class, () -> {
            new Vehicle("ABC-1234", "Ford", "Mustang", "Red", "1800");
        });

        Assertions.assertEquals("The year must be a valid year.", exception.getMessage());
    }

    @Test
    void testValidPlateFormat() {
        Vehicle vehicle = new Vehicle("ABC-1234", "Ford", "Mustang", "Red", "2022");

        Assertions.assertEquals("ABC-1234", vehicle.getPlate());
    }

    @Test
    void testInvalidPlateFormat() {
        Vehicle vehicle = new Vehicle("1234-ABC", "Toyota", "Corolla", "Blue", "2023");

        Set<ConstraintViolation<Vehicle>> violations = validator.validate(vehicle);

        boolean hasPlateViolation = violations.stream()
            .anyMatch(v -> "plate".equals(v.getPropertyPath().toString()) && "The plate must be valid.".equals(v.getMessage()));

        Assertions.assertTrue(hasPlateViolation, "Expected validation error for invalid plate.");
    }

    @Test
    void testBlankPlate() {
        Vehicle vehicle = new Vehicle("", "Chevrolet", "Camaro", "Yellow", "2023");

        Set<ConstraintViolation<Vehicle>> violations = validator.validate(vehicle);

        boolean hasPlateViolation = violations.stream()
            .anyMatch(v -> "plate".equals(v.getPropertyPath().toString()) && "Plate is required.".equals(v.getMessage()));

        Assertions.assertTrue(hasPlateViolation, "Expected validation error for blank plate.");
    }

    @Test
    void testNullPlate() {
        Vehicle vehicle = new Vehicle(null, "Honda", "Civic", "Green", "2021");

        Set<ConstraintViolation<Vehicle>> violations = validator.validate(vehicle);

        boolean hasPlateViolation = violations.stream()
            .anyMatch(v -> "plate".equals(v.getPropertyPath().toString()) && "Plate is required.".equals(v.getMessage()));

        Assertions.assertTrue(hasPlateViolation, "Expected validation error for null plate.");
    }

    @Test
    void testValidBrand() {
        Vehicle vehicle = new Vehicle("XYZ-5678", "Tesla", "Model S", "White", "2022");

        Assertions.assertEquals("Tesla", vehicle.getBrand());
    }

    @Test
    void testBlankBrand() {
        Vehicle vehicle = new Vehicle("DEF-5678", "", "Focus", "Black", "2021");

        Set<ConstraintViolation<Vehicle>> violations = validator.validate(vehicle);

        boolean hasBrandViolation = violations.stream()
            .anyMatch(v -> "brand".equals(v.getPropertyPath().toString()) && "Brand is required.".equals(v.getMessage()));

        Assertions.assertTrue(hasBrandViolation, "Expected validation error for blank brand.");
    }

    @Test
    void testNullBrand() {
        Vehicle vehicle = new Vehicle("DEF-5678", null, "Focus", "Black", "2021");

        Set<ConstraintViolation<Vehicle>> violations = validator.validate(vehicle);

        boolean hasBrandViolation = violations.stream()
            .anyMatch(v -> "brand".equals(v.getPropertyPath().toString()) && "Brand is required.".equals(v.getMessage()));

        Assertions.assertTrue(hasBrandViolation, "Expected validation error for null brand.");
    }

    @Test
    void testValidModel() {
        Vehicle vehicle = new Vehicle("GHI-1234", "BMW", "X5", "Silver", "2023");

        Assertions.assertEquals("X5", vehicle.getModel());
    }

    @Test
    void testBlankModel() {
        Vehicle vehicle = new Vehicle("JKL-4321", "Audi", "", "Red", "2020");

        Set<ConstraintViolation<Vehicle>> violations = validator.validate(vehicle);

        boolean hasModelViolation = violations.stream()
            .anyMatch(v -> "model".equals(v.getPropertyPath().toString()) && "Model is required.".equals(v.getMessage()));

        Assertions.assertTrue(hasModelViolation, "Expected validation error for blank model.");
    }

    @Test
    void testValidColor() {
        Vehicle vehicle = new Vehicle("MNO-5678", "Mercedes", "GLA", "Blue", "2021");

        Assertions.assertEquals("Blue", vehicle.getColor());
    }

    @Test
    void testBlankColor() {
        Vehicle vehicle = new Vehicle("PQR-8765", "Hyundai", "Elantra", "", "2019");

        Set<ConstraintViolation<Vehicle>> violations = validator.validate(vehicle);

        boolean hasColorViolation = violations.stream()
            .anyMatch(v -> "color".equals(v.getPropertyPath().toString()) && "Color is required.".equals(v.getMessage()));

        Assertions.assertTrue(hasColorViolation, "Expected validation error for blank color.");
    }

    @Test
    void testSetValidYear() {
        Vehicle vehicle = new Vehicle("STU-3456", "Kia", "Sorento", "Grey", "2022");
        vehicle.setYear("2023");

        Assertions.assertEquals("2023", vehicle.getYear());
    }

    @Test
    void testSetInvalidYear() {
        Vehicle vehicle = new Vehicle("VWX-7890", "Nissan", "Altima", "Black", "2022");

        Exception exception = Assertions.assertThrows(InvalidException.class, () -> {
            vehicle.setYear("1899");
        });

        Assertions.assertEquals("The year must be a valid year.", exception.getMessage());
    }

    @Test
    void testSetValidPlate() {
        Vehicle vehicle = new Vehicle("ABC-1234", "Ford", "Mustang", "Red", "2020");
        vehicle.setPlate("XYZ-5678");

        Assertions.assertEquals("XYZ-5678", vehicle.getPlate());
    }

    @Test
    void testSetInvalidPlate() {
        Vehicle vehicle = new Vehicle("DEF-5678", "Chevrolet", "Camaro", "Yellow", "2023");
        vehicle.setPlate("1234-XYZ");

        Set<ConstraintViolation<Vehicle>> violations = validator.validate(vehicle);

        boolean hasPlateViolation = violations.stream()
            .anyMatch(v -> "plate".equals(v.getPropertyPath().toString()) && "The plate must be valid.".equals(v.getMessage()));

        Assertions.assertTrue(hasPlateViolation, "Expected validation error for invalid plate.");
    }

    @Test
    void testGettersAndSetters() {
        Vehicle vehicle = new Vehicle();
        vehicle.setPlate("XYZ-1234");
        vehicle.setBrand("Toyota");
        vehicle.setModel("Camry");
        vehicle.setColor("Silver");
        vehicle.setYear("2022");

        Assertions.assertEquals("XYZ-1234", vehicle.getPlate());
        Assertions.assertEquals("Toyota", vehicle.getBrand());
        Assertions.assertEquals("Camry", vehicle.getModel());
        Assertions.assertEquals("Silver", vehicle.getColor());
        Assertions.assertEquals("2022", vehicle.getYear());
    }
    
}
