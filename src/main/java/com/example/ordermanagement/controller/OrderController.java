package com.example.ordermanagement.controller;

import com.example.ordermanagement.form.CreateOrderForm;
import com.example.ordermanagement.model.Customer;
import com.example.ordermanagement.model.CustomerOrder;
import com.example.ordermanagement.model.CustomerOrderItem;
import com.example.ordermanagement.model.FishProduct;
import com.example.ordermanagement.model.OrderStatus;
import com.example.ordermanagement.repository.CustomerOrderRepository;
import com.example.ordermanagement.repository.CustomerRepository;
import com.example.ordermanagement.repository.FishProductRepository;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final CustomerOrderRepository customerOrderRepository;
    private final CustomerRepository customerRepository;
    private final FishProductRepository fishProductRepository;

    public OrderController(
            CustomerOrderRepository customerOrderRepository,
            CustomerRepository customerRepository,
            FishProductRepository fishProductRepository) {
        this.customerOrderRepository = customerOrderRepository;
        this.customerRepository = customerRepository;
        this.fishProductRepository = fishProductRepository;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("orders", customerOrderRepository.findAllByOrderByOrderDateDesc());
        return "orders/index";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Integer id, Model model) {
        CustomerOrder order = customerOrderRepository.findWithItemsById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
        model.addAttribute("order", order);
        model.addAttribute("statuses", OrderStatus.values());
        return "orders/details";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("createOrderForm", new CreateOrderForm());
        populateLists(model);
        return "orders/create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute CreateOrderForm createOrderForm, BindingResult result, Model model) {
        FishProduct fish = null;
        Customer customer = null;

        if (createOrderForm.getFishProductId() != null) {
            fish = fishProductRepository.findById(createOrderForm.getFishProductId()).orElse(null);
            if (fish == null) {
                result.rejectValue("fishProductId", "fishProductId.invalid", "Select a valid fish item.");
            }
        }

        if (createOrderForm.getCustomerId() != null) {
            customer = customerRepository.findById(createOrderForm.getCustomerId()).orElse(null);
            if (customer == null) {
                result.rejectValue("customerId", "customerId.invalid", "Select a valid customer.");
            }
        }

        if (fish != null
                && createOrderForm.getQuantityKg() != null
                && createOrderForm.getQuantityKg().compareTo(fish.getAvailableQuantityKg()) > 0) {
            result.rejectValue(
                    "quantityKg",
                    "quantityKg.stock",
                    "Only " + fish.getAvailableQuantityKg() + " kg is available.");
        }

        if (result.hasErrors() || fish == null || customer == null) {
            populateLists(model);
            return "orders/create";
        }

        CustomerOrder order = new CustomerOrder();
        order.setCustomer(customer);
        order.setOrderDate(LocalDate.now());
        order.setDeliveryDate(createOrderForm.getDeliveryDate());
        order.setDeliveryAddress(resolveDeliveryAddress(createOrderForm.getDeliveryAddress(), customer.getAddress()));
        order.setStatus(OrderStatus.CONFIRMED);

        CustomerOrderItem item = new CustomerOrderItem();
        item.setFishProduct(fish);
        item.setQuantityKg(createOrderForm.getQuantityKg());
        item.setUnitPrice(fish.getUnitPrice());
        order.addItem(item);

        fish.setAvailableQuantityKg(fish.getAvailableQuantityKg().subtract(createOrderForm.getQuantityKg()));
        fishProductRepository.save(fish);
        CustomerOrder savedOrder = customerOrderRepository.save(order);

        return "redirect:/orders/" + savedOrder.getId();
    }

    @PostMapping("/{id}/status")
    public String updateStatus(@PathVariable Integer id, @RequestParam OrderStatus status) {
        CustomerOrder order = customerOrderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
        order.setStatus(status);
        customerOrderRepository.save(order);
        return "redirect:/orders/" + id;
    }

    private void populateLists(Model model) {
        model.addAttribute("customers", customerRepository.findAllByOrderByNameAsc());
        model.addAttribute("fishProducts", fishProductRepository
                .findByAvailableQuantityKgGreaterThanOrderByNameAsc(BigDecimal.ZERO));
    }

    private String resolveDeliveryAddress(String requestedAddress, String customerAddress) {
        if (requestedAddress == null || requestedAddress.isBlank()) {
            return customerAddress;
        }
        return requestedAddress;
    }
}
