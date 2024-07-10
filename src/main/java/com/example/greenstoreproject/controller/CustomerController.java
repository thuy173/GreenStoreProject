package com.example.greenstoreproject.controller;

import com.example.greenstoreproject.bean.request.customer.CustomerUpdateAvatarRequest;
import com.example.greenstoreproject.bean.request.customer.CustomerUpdateRequest;
import com.example.greenstoreproject.bean.response.customer.CustomerResponse;
import com.example.greenstoreproject.service.CustomerService;
import com.example.greenstoreproject.util.SuccessMessage;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Map<String, String>> updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerUpdateRequest customerUpdateRequest) {
        Map<String, String> result = customerService.updateCustomer(id, customerUpdateRequest);
        return ResponseEntity.ok(result);
    }

    @PutMapping(value = "/{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateCustomerAvatar(@PathVariable Long id, @Valid @ModelAttribute CustomerUpdateAvatarRequest customerUpdateRequest) {
        return customerService.updateAvatar(id, customerUpdateRequest);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        return customerService.deleteCustomer(id);
    }
}
