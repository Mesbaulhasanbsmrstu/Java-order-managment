package com.example.ordermanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SupplierDto(
    Integer id,
    
    @NotBlank(message = "Name is required")
    @Size(max = 120, message = "Name must be under 120 characters")
    String name,
    
    @NotBlank(message = "Contact person is required")
    @Size(max = 80, message = "Contact person must be under 80 characters")
    String contactPerson,
    
    @NotBlank(message = "Phone number is required")
    @Size(max = 30, message = "Phone must be under 30 characters")
    String phone,
    
    @Size(max = 180, message = "Landing station must be under 180 characters")
    String landingStation
) {}
