package com.example.greenstoreproject.controller;

import com.example.greenstoreproject.bean.request.address.AddressRequest;
import com.example.greenstoreproject.bean.request.category.CategoryRequest;
import com.example.greenstoreproject.bean.response.address.AddressDetailResponse;
import com.example.greenstoreproject.bean.response.address.AddressResponse;
import com.example.greenstoreproject.bean.response.category.CategoryDetailResponse;
import com.example.greenstoreproject.bean.response.category.CategoryResponse;
import com.example.greenstoreproject.entity.Address;
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
    public AddressDetailResponse getAddressById(@PathVariable Long id) {
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

    @PutMapping("/{customerId}/{addressId}/active")
    public String updateAddressIsActive(

            @PathVariable Long customerId,
            @PathVariable Long addressId,
            @RequestParam Boolean isActive) {
        return addressService.updateAddressIsActive(customerId, addressId, isActive);
    }

    @DeleteMapping("/{id}")
    public String deleteAddress(@PathVariable Long id) {
        return addressService.deleteAddress(id);
    }
}
