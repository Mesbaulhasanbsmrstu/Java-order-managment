package com.example.ordermanagement.controller.api;

import com.example.ordermanagement.dto.SupplierDto;
import com.example.ordermanagement.model.Supplier;
import com.example.ordermanagement.repository.SupplierRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/suppliers")
@Tag(name = "Suppliers", description = "REST APIs for managing fish suppliers")
public class SupplierApiController {

    private final SupplierRepository supplierRepository;

    public SupplierApiController(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @GetMapping
    @Operation(summary = "Get all suppliers", description = "Returns a list of all suppliers sorted alphabetically by name.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of suppliers")
    })
    public List<SupplierDto> getAllSuppliers() {
        return supplierRepository.findAllByOrderByNameAsc().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a supplier by ID", description = "Retrieves details of a specific supplier by their unique identifier.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the supplier details"),
        @ApiResponse(responseCode = "404", description = "Supplier not found")
    })
    public SupplierDto getSupplierById(
            @Parameter(description = "ID of the supplier to fetch", required = true)
            @PathVariable Integer id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier not found with ID: " + id));
        return convertToDto(supplier);
    }

    @PostMapping
    @Operation(summary = "Create a new supplier", description = "Adds a new supplier to the system database.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Supplier successfully created"),
        @ApiResponse(responseCode = "400", description = "Invalid request payload")
    })
    public ResponseEntity<SupplierDto> createSupplier(@Valid @RequestBody SupplierDto supplierDto) {
        Supplier supplier = new Supplier();
        updateSupplierFromDto(supplier, supplierDto);
        Supplier savedSupplier = supplierRepository.save(supplier);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(savedSupplier));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing supplier", description = "Updates details of a supplier matching the given ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Supplier successfully updated"),
        @ApiResponse(responseCode = "400", description = "Invalid request payload"),
        @ApiResponse(responseCode = "404", description = "Supplier not found")
    })
    public SupplierDto updateSupplier(
            @Parameter(description = "ID of the supplier to update", required = true)
            @PathVariable Integer id,
            @Valid @RequestBody SupplierDto supplierDto) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier not found with ID: " + id));
        updateSupplierFromDto(supplier, supplierDto);
        Supplier updatedSupplier = supplierRepository.save(supplier);
        return convertToDto(updatedSupplier);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a supplier", description = "Removes a supplier record from the system database by ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Supplier successfully deleted"),
        @ApiResponse(responseCode = "404", description = "Supplier not found")
    })
    public ResponseEntity<Void> deleteSupplier(
            @Parameter(description = "ID of the supplier to delete", required = true)
            @PathVariable Integer id) {
        if (!supplierRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier not found with ID: " + id);
        }
        supplierRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private SupplierDto convertToDto(Supplier s) {
        return new SupplierDto(
                s.getId(),
                s.getName(),
                s.getContactPerson(),
                s.getPhone(),
                s.getLandingStation()
        );
    }

    private void updateSupplierFromDto(Supplier s, SupplierDto dto) {
        s.setName(dto.name());
        s.setContactPerson(dto.contactPerson());
        s.setPhone(dto.phone());
        s.setLandingStation(dto.landingStation() == null ? "" : dto.landingStation());
    }
}
