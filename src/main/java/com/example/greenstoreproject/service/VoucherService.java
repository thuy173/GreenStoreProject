package com.example.greenstoreproject.service;

import com.example.greenstoreproject.bean.request.voucher.VoucherRequest;
import com.example.greenstoreproject.bean.response.voucher.VoucherResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VoucherService {
    String createVoucher(VoucherRequest voucherRequest);
    VoucherResponse getVoucherById(Long voucherId);
    List<VoucherResponse> getAllVouchers();
    String updateVoucher(Long voucherId, VoucherRequest voucherRequest);
    String deleteVoucher(Long voucherId);
    void activateVoucher(Long voucherId);
}
