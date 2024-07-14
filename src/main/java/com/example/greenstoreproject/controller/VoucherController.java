package com.example.greenstoreproject.controller;

import com.example.greenstoreproject.bean.request.voucher.VoucherRequest;
import com.example.greenstoreproject.bean.response.voucher.VoucherResponse;
import com.example.greenstoreproject.service.VoucherService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    @PostMapping
    public String addVoucher(@Valid @RequestBody VoucherRequest voucherRequest){
        return voucherService.createVoucher(voucherRequest);
    }

    @PutMapping("/{id}")
    public String updateVoucher(@PathVariable Long id, @Valid @RequestBody VoucherRequest voucherRequest){
        return voucherService.updateVoucher(id, voucherRequest);
    }

    @DeleteMapping("/{id}")
    public String deleteVoucher(@PathVariable Long id){
        return voucherService.deleteVoucher(id);
    }
}
