package com.example.ordermanagement.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Suppliers")
public class Supplier {

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
    @Column(name = "ContactPerson", length = 80, nullable = false)
    private String contactPerson = "";

    @NotBlank
    @Size(max = 30)
    @Column(name = "Phone", length = 30, nullable = false)
    private String phone = "";

    @Size(max = 180)
    @Column(name = "LandingStation", length = 180, nullable = false)
    private String landingStation = "";

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private List<SupplyBatch> supplyBatches = new ArrayList<>();

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

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLandingStation() {
        return landingStation;
    }

    public void setLandingStation(String landingStation) {
        this.landingStation = landingStation;
    }

    public List<SupplyBatch> getSupplyBatches() {
        return supplyBatches;
    }

    public void setSupplyBatches(List<SupplyBatch> supplyBatches) {
        this.supplyBatches = supplyBatches;
    }
}
