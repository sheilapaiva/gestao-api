package com.gestao.api.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.transaction.annotation.Transactional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import com.gestao.api.entity.Vehicle;
import com.gestao.api.exception.AlreadyExistsException;
import com.gestao.api.model.VehicleDTO;
import com.gestao.api.repository.VehicleRepository;
import com.gestao.api.service.VehicleService;
import com.gestao.api.exception.NotFoundException;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Optional;

public class VehicleServiceTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private VehicleService vehicleService;

    @Mock
    private CacheManager cacheManager;

    private Cache vehiclesCache;

    public VehicleServiceTest() {
        MockitoAnnotations.openMocks(this);
        vehiclesCache = new org.springframework.cache.concurrent.ConcurrentMapCache("vehiclesCache");
        when(cacheManager.getCache("vehiclesCache")).thenReturn(vehiclesCache);
    }

    @Test
    void testCreateVehicle() {
        VehicleDTO dto = new VehicleDTO(null, "ABC-1234", "Ford", "Mustang", "Red", "2024");
        Vehicle vehicle = new Vehicle("ABC-1234", "Ford", "Mustang", "Red", "2024");
    
        when(vehicleRepository.existsByPlate("ABC-1234")).thenReturn(false);
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);
    
        VehicleDTO createdVehicle = vehicleService.createVehicle(dto);
    
        assertNotNull(createdVehicle, "Created vehicle should not be null");
        assertEquals("ABC-1234", createdVehicle.getPlate(), "Vehicle plate should match");
        assertEquals("Ford", createdVehicle.getBrand(), "Vehicle brand should match");
        assertEquals("Mustang", createdVehicle.getModel(), "Vehicle model should match");
        assertEquals("Red", createdVehicle.getColor(), "Vehicle color should match");
        assertEquals("2024", createdVehicle.getYear(), "Vehicle year should match");
    
        verify(vehicleRepository, times(1)).save(any(Vehicle.class));
    }
    
    @Test
    @Transactional
    void testCreateVehicleAlreadyExists() {
        VehicleDTO dto = new VehicleDTO(null, "ABC-1234", "Ford", "Mustang", "Red", "2024");

        when(vehicleRepository.existsByPlate("ABC-1234")).thenReturn(true);

        Exception exception = assertThrows(AlreadyExistsException.class, () -> {
            vehicleService.createVehicle(dto);
        });

        assertEquals("Vehicle already registered.", exception.getMessage());
    }

    @Test
    @Transactional
    void testFindVehicleById() {
        Vehicle vehicle = new Vehicle("ABC-1234", "Ford", "Mustang", "Red", "2024");
        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));

        VehicleDTO dto = vehicleService.findVehicleById(1L);

        assertNotNull(dto);
        assertEquals("ABC-1234", dto.getPlate());
    }

    @Test
    @Transactional
    void testFindVehicleByIdNotFound() {
        when(vehicleRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class, () -> {
            vehicleService.findVehicleById(1L);
        });

        assertEquals("Vehicle with ID 1 not found.", exception.getMessage());
    }

    @Test
    void testFindAllVehicles() {
        Vehicle vehicle1 = new Vehicle("ABC-1234", "Ford", "Mustang", "Red", "2024");
        Vehicle vehicle2 = new Vehicle("XYZ-5678", "Chevrolet", "Camaro", "Yellow", "2023");

        when(vehicleRepository.findAll()).thenReturn(Arrays.asList(vehicle1, vehicle2));

        List<VehicleDTO> vehicles = vehicleService.findAllVehicles();

        assertNotNull(vehicles);
        assertEquals(2, vehicles.size());
        assertEquals("ABC-1234", vehicles.get(0).getPlate());
        assertEquals("XYZ-5678", vehicles.get(1).getPlate());
    }

    @Test
    void testUpdateVehicle() {
        Vehicle existingVehicle = new Vehicle("ABC-1234", "Ford", "Mustang", "Red", "2024");
        VehicleDTO updatedDTO = new VehicleDTO(null, "XYZ-5678", "Chevrolet", "Camaro", "Yellow", "2023");

        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(existingVehicle));
        when(vehicleRepository.save(any(Vehicle.class))).thenAnswer(invocation -> invocation.getArgument(0));

        VehicleDTO updatedVehicle = vehicleService.updateVehicle(1L, updatedDTO);

        assertNotNull(updatedVehicle);
        assertEquals("XYZ-5678", updatedVehicle.getPlate());
        assertEquals("Chevrolet", updatedVehicle.getBrand());
        assertEquals("Camaro", updatedVehicle.getModel());
        assertEquals("Yellow", updatedVehicle.getColor());
        assertEquals("2023", updatedVehicle.getYear());
    }

    @Test
    void testUpdateVehicleNotFound() {
        VehicleDTO updatedDTO = new VehicleDTO(null, "XYZ-5678", "Chevrolet", "Camaro", "Yellow", "2023");

        when(vehicleRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class, () -> {
            vehicleService.updateVehicle(1L, updatedDTO);
        });

        assertEquals("Vehicle with ID 1 not found.", exception.getMessage());
    }

    @Test
    void testDeleteVehicle() {
        when(vehicleRepository.existsById(1L)).thenReturn(true);

        vehicleService.deleteVehicle(1L);

        verify(vehicleRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteVehicleNotFound() {
        when(vehicleRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(NotFoundException.class, () -> {
            vehicleService.deleteVehicle(1L);
        });

        assertEquals("Vehicle with ID 1 not found.", exception.getMessage());
    }
}
