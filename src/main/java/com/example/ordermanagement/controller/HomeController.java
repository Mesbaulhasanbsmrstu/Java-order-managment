package com.example.ordermanagement.controller;

import com.example.ordermanagement.model.OrderStatus;
import com.example.ordermanagement.repository.CustomerOrderItemRepository;
import com.example.ordermanagement.repository.CustomerOrderRepository;
import com.example.ordermanagement.repository.FishProductRepository;
import com.example.ordermanagement.repository.SupplierRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final SupplierRepository supplierRepository;
    private final FishProductRepository fishProductRepository;
    private final CustomerOrderRepository customerOrderRepository;
    private final CustomerOrderItemRepository customerOrderItemRepository;

    public HomeController(
            SupplierRepository supplierRepository,
            FishProductRepository fishProductRepository,
            CustomerOrderRepository customerOrderRepository,
            CustomerOrderItemRepository customerOrderItemRepository) {
        this.supplierRepository = supplierRepository;
        this.fishProductRepository = fishProductRepository;
        this.customerOrderRepository = customerOrderRepository;
        this.customerOrderItemRepository = customerOrderItemRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        BigDecimal inventoryKg = fishProductRepository.findAll().stream()
                .map(fish -> fish.getAvailableQuantityKg() == null ? BigDecimal.ZERO : fish.getAvailableQuantityKg())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("supplierCount", supplierRepository.count());
        model.addAttribute("fishProductCount", fishProductRepository.count());
        model.addAttribute("pendingOrderCount", customerOrderRepository.countByStatusNotIn(
                List.of(OrderStatus.DELIVERED, OrderStatus.CANCELLED)));
        model.addAttribute("inventoryKg", inventoryKg);
        model.addAttribute("salesValue", customerOrderItemRepository.sumSalesValue());
        model.addAttribute("lowStockFish", fishProductRepository
                .findTop5ByAvailableQuantityKgLessThanEqualOrderByAvailableQuantityKgAsc(new BigDecimal("25.00")));
        model.addAttribute("recentOrders", customerOrderRepository.findTop5ByOrderByOrderDateDesc());
        return "home/index";
    }
}
