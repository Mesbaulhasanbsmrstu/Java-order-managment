package com.example.ordermanagement.form;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

public class SupplyBatchForm {

    @NotNull
    private Integer supplierId;

    @NotNull
    private Integer fishProductId;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal quantityKg = BigDecimal.ZERO;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal costPerKg = BigDecimal.ZERO;

    @NotNull
    private LocalDate receivedDate = LocalDate.now();

    @Size(max = 120)
    private String storageLocation = "Cold Storage";

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getFishProductId() {
        return fishProductId;
    }

    public void setFishProductId(Integer fishProductId) {
        this.fishProductId = fishProductId;
    }

    public BigDecimal getQuantityKg() {
        return quantityKg;
    }

    public void setQuantityKg(BigDecimal quantityKg) {
        this.quantityKg = quantityKg;
    }

    public BigDecimal getCostPerKg() {
        return costPerKg;
    }

    public void setCostPerKg(BigDecimal costPerKg) {
        this.costPerKg = costPerKg;
    }

    public LocalDate getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(LocalDate receivedDate) {
        this.receivedDate = receivedDate;
    }

    public String getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(String storageLocation) {
        this.storageLocation = storageLocation;
    }
}
