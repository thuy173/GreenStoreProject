package com.example.greenstoreproject.bean.request.voucher;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VoucherRequest {

    private Double discount;

    private Double minOrderAmount;

    private LocalDateTime expiryDate;

    private Boolean status;

}
