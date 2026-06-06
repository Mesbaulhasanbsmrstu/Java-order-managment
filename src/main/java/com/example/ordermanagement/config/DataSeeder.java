package com.example.ordermanagement.config;

import com.example.ordermanagement.model.Customer;
import com.example.ordermanagement.model.FishProduct;
import com.example.ordermanagement.model.Supplier;
import com.example.ordermanagement.model.SupplyBatch;
import com.example.ordermanagement.repository.CustomerRepository;
import com.example.ordermanagement.repository.FishProductRepository;
import com.example.ordermanagement.repository.SupplierRepository;
import com.example.ordermanagement.repository.SupplyBatchRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final SupplierRepository supplierRepository;
    private final CustomerRepository customerRepository;
    private final FishProductRepository fishProductRepository;
    private final SupplyBatchRepository supplyBatchRepository;

    public DataSeeder(
            SupplierRepository supplierRepository,
            CustomerRepository customerRepository,
            FishProductRepository fishProductRepository,
            SupplyBatchRepository supplyBatchRepository) {
        this.supplierRepository = supplierRepository;
        this.customerRepository = customerRepository;
        this.fishProductRepository = fishProductRepository;
        this.supplyBatchRepository = supplyBatchRepository;
    }

    @Override
    public void run(String... args) {
        if (supplierRepository.count() > 0) {
            return;
        }

        Supplier supplier = new Supplier();
        supplier.setName("Cox's Bazar Marine Supply");
        supplier.setContactPerson("Rahim Uddin");
        supplier.setPhone("01700000001");
        supplier.setLandingStation("Cox's Bazar");
        supplierRepository.save(supplier);

        Customer customer = new Customer();
        customer.setName("Blue Bay Restaurant");
        customer.setPhone("01700000002");
        customer.setAddress("Gulshan, Dhaka");
        customerRepository.save(customer);

        FishProduct fish = new FishProduct();
        fish.setName("Hilsa");
        fish.setCategory("Sea Fish");
        fish.setAvailableQuantityKg(new BigDecimal("120.00"));
        fish.setUnitPrice(new BigDecimal("950.00"));
        fish.setGrade("Premium");
        fishProductRepository.save(fish);

        SupplyBatch batch = new SupplyBatch();
        batch.setSupplier(supplier);
        batch.setFishProduct(fish);
        batch.setQuantityKg(new BigDecimal("120.00"));
        batch.setCostPerKg(new BigDecimal("720.00"));
        batch.setReceivedDate(LocalDate.now());
        batch.setStorageLocation("Freezer A1");
        supplyBatchRepository.save(batch);
    }
}
