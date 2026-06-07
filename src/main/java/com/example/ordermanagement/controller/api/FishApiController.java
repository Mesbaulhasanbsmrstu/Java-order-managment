package com.example.ordermanagement.controller.api;

import com.example.ordermanagement.dto.FishDto;
import com.example.ordermanagement.dto.SupplierDto;
import com.example.ordermanagement.model.FishProduct;
import com.example.ordermanagement.model.Supplier;
import com.example.ordermanagement.repository.FishProductRepository;
import com.example.ordermanagement.repository.SupplierRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/fishes")
@Tag(name = "FishProducts", description = "REST APIs for managing fish product")
public class FishApiController {
    private final FishProductRepository fishRepository;

    public FishApiController(FishProductRepository fishRepository) {
        this.fishRepository = fishRepository;
    }

    @GetMapping
    @Operation(summary = "Get all fish products", description = "Returns a list of all fishes sorted alphabetically by name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of fish products")
    })
    public List<FishDto> getAllFishes() {
        return fishRepository.findAllByOrderByNameAsc().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a fish product by ID", description = "Retrieves details of a specific fish product by their unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the fish product details"),
            @ApiResponse(responseCode = "404", description = "Fish product not found")
    })
    public FishDto getSupplierById(
            @Parameter(description = "ID of the fish product to fetch", required = true)
            @PathVariable Integer id) {
        FishProduct fish = fishRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier not found with ID: " + id));
        return convertToDto(fish);
    }

    @PostMapping
    @Operation(summary = "Create a new fish product", description = "Adds a new fish product to the system database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Fish product successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload")
    })
    public ResponseEntity<FishDto> createFish(@Valid @RequestBody FishDto fishDto) {
        FishProduct fish = new FishProduct();
        updateFishFromDto(fish, fishDto);
        FishProduct savedFish = fishRepository.save(fish);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(savedFish));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing fish product", description = "Updates details of a fish product matching the given ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fish product successfully updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload"),
            @ApiResponse(responseCode = "404", description = "Fish product not found")
    })
    public FishDto updateFish(
            @Parameter(description = "ID of the fish to update", required = true)
            @PathVariable Integer id,
            @Valid @RequestBody FishDto fishDto) {
        FishProduct fish = fishRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fish product not found with ID: " + id));
        updateFishFromDto(fish, fishDto);
        FishProduct updatedFish = fishRepository.save(fish);
        return convertToDto(updatedFish);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a supplier", description = "Removes a fish product record from the system database by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Fish product successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Fish product not found")
    })
    public ResponseEntity<Void> deleteFish(
            @Parameter(description = "ID of the fish product to delete", required = true)
            @PathVariable Integer id) {
        if (!fishRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Fish product not found with ID: " + id);
        }
        fishRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private FishDto convertToDto(FishProduct s) {
        return new FishDto(
                s.getId(),
                s.getName(),
                s.getCategory(),

                s.getAvailableQuantityKg(),
                s.getUnitPrice(),
                s.getGrade()
        );
    }

    private void updateFishFromDto(FishProduct s, FishDto dto) {
        s.setName(dto.name());
        s.setCategory(dto.category());
        s.setAvailableQuantityKg(dto.availableQuantityKg());
        s.setUnitPrice(dto.unitPrice());
        s.setGrade(dto.grade());
    }
}
