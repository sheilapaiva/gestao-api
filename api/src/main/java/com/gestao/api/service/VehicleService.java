package com.gestao.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestao.api.entity.Vehicle;
import com.gestao.api.repository.VehicleRepository;
import com.gestao.api.exception.AlreadyExistsException;
import com.gestao.api.exception.NotFoundException;
import com.gestao.api.model.VehicleDTO;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleService {

    private static final Logger logger = LoggerFactory.getLogger(VehicleService.class);
 
    @Autowired
    private VehicleRepository vehicleRepository;

    @Transactional
    public VehicleDTO createVehicle(VehicleDTO vehicle) {
        logger.info("Starting creation of vehicle: {}", vehicle);

        if (vehicleRepository.existsByPlate(vehicle.getPlate())) {
            logger.error("Vehicle with plate {} is already registered.", vehicle.getPlate());
            throw new AlreadyExistsException("Vehicle already registered.");
        }

        Vehicle newVehicle = new Vehicle(vehicle.getPlate(), vehicle.getBrand(), vehicle.getModel(), vehicle.getColor(), vehicle.getYear());
        Vehicle savedVehicle = vehicleRepository.save(newVehicle);
        logger.info("Vehicle created successfully: {}", savedVehicle);

        return new VehicleDTO(savedVehicle.getId(), savedVehicle.getPlate(), savedVehicle.getBrand(), savedVehicle.getModel(), savedVehicle.getColor(), savedVehicle.getYear());
    }

    @Transactional
    public VehicleDTO findVehicleById(Long id) {
        logger.info("Searching for vehicle with ID {}", id);

        Vehicle vehicle = vehicleRepository.findById(id).orElse(null);

        if(vehicle == null) {
            logger.error("Vehicle with ID {} not found.", id);
            throw new NotFoundException("Vehicle with ID " + id + " not found.");
        }

        return new VehicleDTO(vehicle.getId(), vehicle.getPlate(), vehicle.getBrand(), vehicle.getModel(), vehicle.getColor(), vehicle.getYear());
    }

    @Transactional
    public List<VehicleDTO> findAllVehicles() {
        logger.info("Fetching all vehicles");

        return vehicleRepository.findAll().stream()
                .map(vehicle -> new VehicleDTO(vehicle.getId(), vehicle.getPlate(), vehicle.getBrand(), vehicle.getModel(), vehicle.getColor(), vehicle.getYear()))
                .collect(Collectors.toList());
    }

    @Transactional
    public VehicleDTO updateVehicle(Long id, VehicleDTO vehicle) {
        logger.info("Updating vehicle with ID {}", id);

        Vehicle existingVehicle = vehicleRepository.findById(id).orElse(null);

        if (existingVehicle == null) {
            logger.error("Vehicle with ID {} not found for update.", id);
            throw new NotFoundException("Vehicle with ID " + id + " not found.");
        }

        existingVehicle.setPlate(vehicle.getPlate());
        existingVehicle.setColor(vehicle.getColor());
        existingVehicle.setBrand(vehicle.getBrand());
        existingVehicle.setModel(vehicle.getModel());
        existingVehicle.setYear(vehicle.getYear());

        Vehicle updatedVehicle = vehicleRepository.save(existingVehicle);
        logger.info("Vehicle updated successfully: {}", updatedVehicle);

        return new VehicleDTO(updatedVehicle.getId(), updatedVehicle.getPlate(), updatedVehicle.getBrand(), updatedVehicle.getModel(), updatedVehicle.getColor(), updatedVehicle.getYear());
    }

    @Transactional
    public void deleteVehicle(Long id) {
        logger.info("Deleting vehicle with ID {}", id);

        if (!vehicleRepository.existsById(id)) {
            logger.error("Vehicle with ID {} not found for deletion.", id);
            throw new NotFoundException("Vehicle with ID " + id + " not found.");
        }

        vehicleRepository.deleteById(id);
        logger.info("Vehicle with ID {} deleted successfully.", id);
    }
    
}
