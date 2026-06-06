package com.example.ordermanagement.form;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateOrderForm {

    @NotNull
    private Integer customerId;

    @NotNull
    private Integer fishProductId;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal quantityKg;

    @NotNull
    private LocalDate deliveryDate = LocalDate.now().plusDays(1);

    @Size(max = 220)
    private String deliveryAddress = "";

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
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

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}
