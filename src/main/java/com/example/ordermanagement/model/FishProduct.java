package com.example.ordermanagement.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "FishProducts")
public class FishProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @NotBlank
    @Size(max = 120)
    @Column(name = "Name", length = 120, nullable = false)
    private String name = "";

    @NotBlank
    @Size(max = 80)
    @Column(name = "Category", length = 80, nullable = false)
    private String category = "Sea Fish";

    @NotNull
    @DecimalMin("0.00")
    @Column(name = "AvailableQuantityKg", precision = 18, scale = 2, nullable = false)
    private BigDecimal availableQuantityKg = BigDecimal.ZERO;

    @NotNull
    @DecimalMin("0.01")
    @Column(name = "UnitPrice", precision = 18, scale = 2, nullable = false)
    private BigDecimal unitPrice = BigDecimal.ZERO;

    @Size(max = 60)
    @Column(name = "Grade", length = 60, nullable = false)
    private String grade = "A";

    @OneToMany(mappedBy = "fishProduct", cascade = CascadeType.ALL)
    private List<SupplyBatch> supplyBatches = new ArrayList<>();

    @OneToMany(mappedBy = "fishProduct")
    private List<CustomerOrderItem> orderItems = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getAvailableQuantityKg() {
        return availableQuantityKg;
    }

    public void setAvailableQuantityKg(BigDecimal availableQuantityKg) {
        this.availableQuantityKg = availableQuantityKg;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public List<SupplyBatch> getSupplyBatches() {
        return supplyBatches;
    }

    public void setSupplyBatches(List<SupplyBatch> supplyBatches) {
        this.supplyBatches = supplyBatches;
    }

    public List<CustomerOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<CustomerOrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
