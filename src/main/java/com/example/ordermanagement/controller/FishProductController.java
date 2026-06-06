package com.example.ordermanagement.controller;

import com.example.ordermanagement.model.FishProduct;
import com.example.ordermanagement.repository.FishProductRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/fish-products")
public class FishProductController {

    private final FishProductRepository fishProductRepository;

    public FishProductController(FishProductRepository fishProductRepository) {
        this.fishProductRepository = fishProductRepository;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("fishProducts", fishProductRepository.findAllByOrderByNameAsc());
        return "fish-products/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("fishProduct", new FishProduct());
        return "fish-products/create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute FishProduct fishProduct, BindingResult result) {
        if (result.hasErrors()) {
            return "fish-products/create";
        }

        fishProductRepository.save(fishProduct);
        return "redirect:/fish-products";
    }
}
