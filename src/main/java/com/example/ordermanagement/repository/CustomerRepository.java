package com.example.ordermanagement.repository;

import com.example.ordermanagement.model.Customer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    List<Customer> findAllByOrderByNameAsc();
}
