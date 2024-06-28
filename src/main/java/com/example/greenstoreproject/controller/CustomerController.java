package com.example.greenstoreproject.controller;

import com.example.greenstoreproject.bean.request.category.CategoryRequest;
import com.example.greenstoreproject.bean.request.customer.CustomerUpdateRequest;
import com.example.greenstoreproject.bean.response.category.CategoryDetailResponse;
import com.example.greenstoreproject.bean.response.category.CategoryResponse;
import com.example.greenstoreproject.bean.response.customer.CustomerResponse;
import com.example.greenstoreproject.service.CustomerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")
@SecurityRequirement(name = "bearerAuth")
public class CustomerController {
    private final CustomerService customerService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public List<CustomerResponse> getAllCustomer() {
        return customerService.getAllCustomer();
    }

    @GetMapping("/{id}")
    public CustomerResponse getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @PutMapping("/{id}")
    public String updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerUpdateRequest customerUpdateRequest) {
        return customerService.updateCustomer(id, customerUpdateRequest);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        return customerService.deleteCustomer(id);
    }
}
