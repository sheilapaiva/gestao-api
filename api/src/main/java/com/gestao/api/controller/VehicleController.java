package com.gestao.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.gestao.api.service.VehicleService;
import jakarta.validation.Valid;
import com.gestao.api.model.VehicleDTO;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private static final Logger logger = LoggerFactory.getLogger(VehicleController.class);

    @Autowired
    private VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<VehicleDTO> createVehicle(@Valid @RequestBody VehicleDTO vehicle) {
        logger.info("Creating new vehicle: {}", vehicle);
        VehicleDTO createdVehicle = vehicleService.createVehicle(vehicle);
        logger.info("Vehicle created successfully: {}", createdVehicle);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdVehicle);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleDTO> obterVehicleById(@PathVariable Long id) {
        logger.info("Fetching vehicle with ID: {}", id);
        VehicleDTO vehicle = vehicleService.findVehicleById(id);
        logger.info("Vehicle retrieved: {}", vehicle);
        return ResponseEntity.ok(vehicle);
    }

    @GetMapping
    public ResponseEntity<List<VehicleDTO>> findAllVehicles() {
        logger.info("Fetching all vehicles");
        List<VehicleDTO> vehicles = vehicleService.findAllVehicles();
        logger.info("Total vehicles retrieved: {}", vehicles.size());
        return ResponseEntity.ok(vehicles);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleDTO> updateVehicle(@PathVariable Long id, @Valid @RequestBody VehicleDTO vehicle) {
        logger.info("Updating vehicle with ID: {}. New data: {}", id, vehicle);
        VehicleDTO updatedVehicle = vehicleService.updateVehicle(id, vehicle);
        logger.info("Vehicle updated successfully: {}", updatedVehicle);
        return ResponseEntity.ok(updatedVehicle);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        logger.info("Deleting vehicle with ID: {}", id);
        vehicleService.deleteVehicle(id);
        logger.info("Vehicle deleted successfully");
        return ResponseEntity.noContent().build();
    }

}
