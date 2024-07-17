package com.example.greenstoreproject.bean.response.voucher;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VoucherResponse {

    private Long voucherId;

    private String code;

    private Double discount;

    private Double minOrderAmount;

    private LocalDateTime expiryDate;

    private Boolean status;

}
