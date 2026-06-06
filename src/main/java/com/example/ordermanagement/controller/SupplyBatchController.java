package com.example.ordermanagement.controller;

import com.example.ordermanagement.form.SupplyBatchForm;
import com.example.ordermanagement.model.FishProduct;
import com.example.ordermanagement.model.Supplier;
import com.example.ordermanagement.model.SupplyBatch;
import com.example.ordermanagement.repository.FishProductRepository;
import com.example.ordermanagement.repository.SupplierRepository;
import com.example.ordermanagement.repository.SupplyBatchRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequestMapping("/supply-batches")
public class SupplyBatchController {

    private final SupplyBatchRepository supplyBatchRepository;
    private final SupplierRepository supplierRepository;
    private final FishProductRepository fishProductRepository;

    public SupplyBatchController(
            SupplyBatchRepository supplyBatchRepository,
            SupplierRepository supplierRepository,
            FishProductRepository fishProductRepository) {
        this.supplyBatchRepository = supplyBatchRepository;
        this.supplierRepository = supplierRepository;
        this.fishProductRepository = fishProductRepository;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("batches", supplyBatchRepository.findAllByOrderByReceivedDateDesc());
        return "supply-batches/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("supplyBatchForm", new SupplyBatchForm());
        populateLists(model);
        return "supply-batches/create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute SupplyBatchForm supplyBatchForm, BindingResult result, Model model) {
        if (result.hasErrors()) {
            populateLists(model);
            return "supply-batches/create";
        }

        Supplier supplier = supplierRepository.findById(supplyBatchForm.getSupplierId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
        FishProduct fish = fishProductRepository.findById(supplyBatchForm.getFishProductId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));

        fish.setAvailableQuantityKg(fish.getAvailableQuantityKg().add(supplyBatchForm.getQuantityKg()));

        SupplyBatch batch = new SupplyBatch();
        batch.setSupplier(supplier);
        batch.setFishProduct(fish);
        batch.setQuantityKg(supplyBatchForm.getQuantityKg());
        batch.setCostPerKg(supplyBatchForm.getCostPerKg());
        batch.setReceivedDate(supplyBatchForm.getReceivedDate());
        batch.setStorageLocation(supplyBatchForm.getStorageLocation());

        fishProductRepository.save(fish);
        supplyBatchRepository.save(batch);
        return "redirect:/supply-batches";
    }

    private void populateLists(Model model) {
        model.addAttribute("suppliers", supplierRepository.findAllByOrderByNameAsc());
        model.addAttribute("fishProducts", fishProductRepository.findAllByOrderByNameAsc());
    }
}
