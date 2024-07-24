package com.example.greenstoreproject.controller;

import com.example.greenstoreproject.bean.request.voucher.VoucherRequest;
import com.example.greenstoreproject.bean.response.voucher.VoucherResponse;
import com.example.greenstoreproject.service.VoucherService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/voucher")
@SecurityRequirement(name = "bearerAuth")
public class VoucherController {
    private final VoucherService voucherService;

    @GetMapping
    public List<VoucherResponse> getAllVoucher() {
        return voucherService.getAllVouchers();
    }

    @GetMapping("/active")
    public List<VoucherResponse> getActiveVoucher() {
        return voucherService.getActiveVouchers();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public String addVoucher(@Valid @RequestBody VoucherRequest voucherRequest){
        return voucherService.createVoucher(voucherRequest);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public String updateVoucher(@PathVariable Long id, @Valid @RequestBody VoucherRequest voucherRequest){
        return voucherService.updateVoucher(id, voucherRequest);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public String deleteVoucher(@PathVariable Long id){
        return voucherService.deleteVoucher(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/active/{id}")
    public ResponseEntity<Void> activeVoucher(@PathVariable Long id) {
        voucherService.activateVoucher(id);
        return ResponseEntity.noContent().build();
    }
}
