package com.example.ordermanagement.repository;

import com.example.ordermanagement.model.SupplyBatch;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplyBatchRepository extends JpaRepository<SupplyBatch, Integer> {
    @EntityGraph(attributePaths = {"supplier", "fishProduct"})
    List<SupplyBatch> findAllByOrderByReceivedDateDesc();
}
