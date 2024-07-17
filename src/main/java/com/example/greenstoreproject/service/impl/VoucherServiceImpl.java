package com.example.greenstoreproject.service.impl;

import com.example.greenstoreproject.bean.request.voucher.VoucherRequest;
import com.example.greenstoreproject.bean.response.voucher.VoucherResponse;
import com.example.greenstoreproject.entity.Voucher;
import com.example.greenstoreproject.mapper.VoucherMapper;
import com.example.greenstoreproject.repository.VoucherRepository;
import com.example.greenstoreproject.service.VoucherService;
import com.example.greenstoreproject.util.SuccessMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoucherServiceImpl implements VoucherService {
    private final VoucherRepository voucherRepository;
    private final VoucherMapper voucherMapper;

    @Override
    public String createVoucher(VoucherRequest voucherRequest) {
        Voucher voucher = voucherMapper.toEntity(voucherRequest);
        voucherRepository.save(voucher);
        return SuccessMessage.SUCCESS_CREATED.getMessage();
    }

    @Override
    public VoucherResponse getVoucherById(Long voucherId) {
        Voucher voucher = voucherRepository.findById(voucherId).orElseThrow(() -> new RuntimeException("Voucher not found"));
        return voucherMapper.toDto(voucher);
    }

    @Override
    public List<VoucherResponse> getAllVouchers() {
        return voucherRepository.findAll().stream()
                .map(voucherMapper::toDto)
                .collect(Collectors.toList());
    }

    @Scheduled(fixedRate = 60000)
    public void updateExpiredVouchers() {
        List<Voucher> vouchers = voucherRepository.findAll();
        for (Voucher voucher : vouchers) {
            if (voucher.getExpiryDate().isBefore(LocalDateTime.now()) && Boolean.TRUE.equals(voucher.getStatus())) {
                voucher.setStatus(false);
                voucherRepository.save(voucher);
            }
        }
    }

    @Override
    public String updateVoucher(Long voucherId, VoucherRequest voucherRequest) {
        Voucher voucher = voucherRepository.findById(voucherId).orElseThrow(() -> new RuntimeException("Voucher not found"));
        voucherMapper.updateEntityFromDto(voucherRequest, voucher);
        voucherRepository.save(voucher);
        return SuccessMessage.SUCCESS_UPDATED.getMessage();
    }

    @Override
    public String deleteVoucher(Long voucherId) {
        Voucher voucher = voucherRepository.findById(voucherId).orElseThrow(() -> new RuntimeException("Voucher not found"));
        voucher.setStatus(false);
        voucherRepository.save(voucher);
        return SuccessMessage.SUCCESS_DELETED.getMessage();
    }

}
