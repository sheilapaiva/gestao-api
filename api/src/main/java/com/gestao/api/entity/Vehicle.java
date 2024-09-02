package com.gestao.api.entity;

import com.gestao.api.exception.InvalidException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "table_vehicle")
public class Vehicle {
    
    @Id
    @Column(unique = true, nullable = false, length = 20)
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Plate is required.")
    @Pattern(regexp = "([A-Z]{3}-[0-9]{4}|[A-Z]{3}[0-9][A-Z][0-9]{2})", message = "The plate must be valid.")
    private String plate; 

    @Column(nullable = false)
    @NotBlank(message = "Brand is required.")
    private String brand;

    @Column(nullable = false)
    @NotBlank(message = "Model is required.")
    private String model;

    @Column(nullable = false)
    @NotBlank(message = "Color is required.")
    private String color;

    @Column(nullable = false)
    @NotBlank(message = "Year is required.")
    @Pattern(regexp = "[0-9]{4}", message = "The year must be a valid year.")
    private String year;

    public Vehicle() {
    }
    
    public Vehicle(String plate, String brand, String model, String color, String year) {
        validateYear(year);
        this.plate = plate;
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.year = year;
    }

    public Long getId() {
        return id;
    }

    public String getPlate() {
        return plate;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }

    public String getYear() {
        return year;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public void setYear(String year) {
        validateYear(year);
        this.year = year;
    }

    private void validateYear(String year) {
        if (!isYearValid(year)) {
            throw new InvalidException("The year must be a valid year.");
        }
    }

    private boolean isYearValid(String year) {
        if (year == null || !year.matches("[0-9]{4}")) {
            return false;
        }
        int currentYear = java.time.Year.now().getValue();
        int yearValue = Integer.parseInt(year);
        return yearValue >= 1900 && yearValue <= currentYear;
    }

    public String toString() {
        return "Vehicle [plate=" + plate + ", brand=" + brand + ", model=" + model + ", color=" + color + ", year=" + year + "]";
    }
}
