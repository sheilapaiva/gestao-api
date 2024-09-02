package com.gestao.api.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class VehicleDTO {

    public Long id;

    @NotBlank(message = "Plate is required.")
    @Pattern(regexp = "([A-Z]{3}-[0-9]{4}|[A-Z]{3}[0-9][A-Z][0-9]{2})", message = "The plate must be valid.")
    private String plate; 

    @NotBlank(message = "Brand is required.")
    private String brand;

    @NotBlank(message = "Model is required.")
    private String model;

    @NotBlank(message = "Color is required.")
    private String color;

    @NotBlank(message = "Year is required.")
    @Pattern(regexp = "[0-9]{4}", message = "The year must be a valid year.")
    private String year;

    public VehicleDTO() {
    }

    public VehicleDTO(Long id, String plate, String brand, String model, String color, String year) {
        this.id = id;
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
}
