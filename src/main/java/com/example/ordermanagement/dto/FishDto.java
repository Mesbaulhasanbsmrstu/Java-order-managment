package com.example.ordermanagement.dto;

import com.example.ordermanagement.model.CustomerOrderItem;
import com.example.ordermanagement.model.SupplyBatch;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public record FishDto (

Integer id,

@NotBlank(message = "Name is required")
@Size(max = 120, message = "Name must be under 120 characters")
String name,


@NotBlank(message = "Category is required")
@Size(max = 80,message = "category must be under 80 characters")
String category,

        @NotNull(message = "Available Quantity is required")
        @DecimalMin("0.00")
 BigDecimal availableQuantityKg,

@NotNull(message = "Available Quantity is required")
@DecimalMin("0.01")
 BigDecimal unitPrice,

@Size(max = 60)
@Column(name = "Grade", length = 60, nullable = false)
 String grade
    ){}
