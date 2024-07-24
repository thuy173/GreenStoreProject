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
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
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

    @Override
    public List<VoucherResponse> getActiveVouchers() {
        return voucherRepository.findActiveVoucher().stream()
                .map(voucherMapper::toDto)
                .collect(Collectors.toList());
    }

    @Scheduled(fixedRate = 60000)
    public void updateExpiredVouchers() {
        List<Voucher> vouchers = voucherRepository.findAll();
        for (Voucher voucher : vouchers) {
            LocalDateTime nowUtc = LocalDateTime.now(ZoneOffset.UTC);
            if (voucher.getExpiryDate().isBefore(nowUtc) && Boolean.TRUE.equals(voucher.getStatus())) {
                voucher.setStatus(false);
                voucherRepository.save(voucher);
            }
        }
    }

    @Override
    public String updateVoucher(Long voucherId, VoucherRequest voucherRequest) {
        Voucher voucher = voucherRepository.findById(voucherId).orElseThrow(() -> new RuntimeException("Voucher not found"));
        System.out.println("Before update: " + voucher);
        voucherMapper.updateEntityFromDto(voucherRequest, voucher);
        System.out.println("After update: " + voucher);
        voucher.setExpiryDate(voucher.getExpiryDate().atOffset(ZoneOffset.UTC).toLocalDateTime());
        Voucher savedVoucher = voucherRepository.save(voucher);
        System.out.println("Saved voucher: " + savedVoucher);
        return SuccessMessage.SUCCESS_UPDATED.getMessage();
    }

    @Override
    public String deleteVoucher(Long voucherId) {
        Voucher voucher = voucherRepository.findById(voucherId).orElseThrow(() -> new RuntimeException("Voucher not found"));
        voucher.setStatus(false);
        voucherRepository.save(voucher);
        return SuccessMessage.SUCCESS_DELETED.getMessage();
    }

    @Override
    public void activateVoucher(Long voucherId) {
        Optional<Voucher> vouchers = voucherRepository.findById(voucherId);

        if(vouchers.isPresent()){
            Voucher voucher = vouchers.get();
            voucher.setStatus(true);
            voucherRepository.save(voucher);
        }
    }

}
