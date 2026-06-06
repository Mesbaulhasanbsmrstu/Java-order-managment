package com.example.ordermanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "SupplyBatches")
public class SupplyBatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SupplierId", nullable = false)
    private Supplier supplier;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FishProductId", nullable = false)
    private FishProduct fishProduct;

    @NotNull
    @DecimalMin("0.01")
    @Column(name = "QuantityKg", precision = 18, scale = 2, nullable = false)
    private BigDecimal quantityKg = BigDecimal.ZERO;

    @NotNull
    @DecimalMin("0.01")
    @Column(name = "CostPerKg", precision = 18, scale = 2, nullable = false)
    private BigDecimal costPerKg = BigDecimal.ZERO;

    @NotNull
    @Column(name = "ReceivedDate", nullable = false)
    private LocalDate receivedDate = LocalDate.now();

    @Size(max = 120)
    @Column(name = "StorageLocation", length = 120, nullable = false)
    private String storageLocation = "Cold Storage";

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public FishProduct getFishProduct() {
        return fishProduct;
    }

    public void setFishProduct(FishProduct fishProduct) {
        this.fishProduct = fishProduct;
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
