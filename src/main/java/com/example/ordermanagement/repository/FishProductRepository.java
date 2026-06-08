package com.example.ordermanagement.repository;

import com.example.ordermanagement.dto.FishDto;
import com.example.ordermanagement.model.FishProduct;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FishProductRepository extends JpaRepository<FishProduct, Integer> {
    List<FishProduct> findAllByOrderByNameAsc();
    @Query(value = """
                SELECT new com.example.ordermanagement.dto.FishDto(
                    f.id,
                    "xxxxx",
                    f.category,
                    f.availableQuantityKg,
                    f.unitPrice,
                    f.grade
                )
                FROM FishProduct f
                WHERE f.id = :id
            """)
    Optional<FishDto> findDtoById(@Param("id") Integer id);
    List<FishProduct> findByAvailableQuantityKgGreaterThanOrderByNameAsc(BigDecimal quantity);

    List<FishProduct> findTop5ByAvailableQuantityKgLessThanEqualOrderByAvailableQuantityKgAsc(BigDecimal quantity);
}
