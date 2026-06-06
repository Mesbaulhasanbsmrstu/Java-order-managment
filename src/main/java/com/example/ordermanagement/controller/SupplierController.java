package com.example.ordermanagement.controller;

import com.example.ordermanagement.model.Supplier;
import com.example.ordermanagement.repository.SupplierRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/suppliers")
public class SupplierController {

    private final SupplierRepository supplierRepository;

    public SupplierController(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("suppliers", supplierRepository.findAllByOrderByNameAsc());
        return "suppliers/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("supplier", new Supplier());
        return "suppliers/create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute Supplier supplier, BindingResult result) {
        if (result.hasErrors()) {
            return "suppliers/create";
        }

        supplierRepository.save(supplier);
        return "redirect:/suppliers";
    }
}
