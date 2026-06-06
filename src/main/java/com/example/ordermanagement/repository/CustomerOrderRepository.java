package com.example.ordermanagement.repository;

import com.example.ordermanagement.model.CustomerOrder;
import com.example.ordermanagement.model.OrderStatus;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Integer> {
    long countByStatusNotIn(Collection<OrderStatus> statuses);

    @EntityGraph(attributePaths = {"customer", "items", "items.fishProduct"})
    List<CustomerOrder> findAllByOrderByOrderDateDesc();

    @EntityGraph(attributePaths = {"customer", "items", "items.fishProduct"})
    List<CustomerOrder> findTop5ByOrderByOrderDateDesc();

    @EntityGraph(attributePaths = {"customer", "items", "items.fishProduct"})
    Optional<CustomerOrder> findWithItemsById(Integer id);
}
