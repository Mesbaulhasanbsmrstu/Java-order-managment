package com.example.ordermanagement.repository;

import com.example.ordermanagement.model.CustomerOrderItem;
import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerOrderItemRepository extends JpaRepository<CustomerOrderItem, Integer> {
    @Query("select coalesce(sum(i.quantityKg * i.unitPrice), 0) from CustomerOrderItem i")
    BigDecimal sumSalesValue();
}
