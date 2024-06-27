package com.example.greenstoreproject.controller;

import com.example.greenstoreproject.bean.request.address.AddressRequest;
import com.example.greenstoreproject.bean.request.category.CategoryRequest;
import com.example.greenstoreproject.bean.response.address.AddressResponse;
import com.example.greenstoreproject.bean.response.category.CategoryDetailResponse;
import com.example.greenstoreproject.bean.response.category.CategoryResponse;
import com.example.greenstoreproject.service.AddressService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/address")
@SecurityRequirement(name = "bearerAuth")
public class AddressController {
    private final AddressService addressService;

    @GetMapping
    public List<AddressResponse> getAllAddress() {
        return addressService.getAllAddress();
    }

    @GetMapping("/{id}")
    public AddressResponse getAddressById(@PathVariable Long id) {
        return addressService.getAddressById(id);
    }

    @PostMapping
    public String addAddress(@Valid @RequestBody AddressRequest addressRequest) {
        return addressService.createAddress(addressRequest);
    }

    @PutMapping("/{id}")
    public String updateAddress(@PathVariable Long id, @Valid @RequestBody AddressRequest addressRequest) {
        return addressService.updateAddress(id, addressRequest);
    }

    @DeleteMapping("/{id}")
    public String deleteAddress(@PathVariable Long id) {
        return addressService.deleteAddress(id);
    }
}
