package com.example.ordermanagement.repository;

import com.example.ordermanagement.model.FishProduct;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FishProductRepository extends JpaRepository<FishProduct, Integer> {
    List<FishProduct> findAllByOrderByNameAsc();

    List<FishProduct> findByAvailableQuantityKgGreaterThanOrderByNameAsc(BigDecimal quantity);

    List<FishProduct> findTop5ByAvailableQuantityKgLessThanEqualOrderByAvailableQuantityKgAsc(BigDecimal quantity);
}
