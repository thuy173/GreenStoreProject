package com.example.greenstoreproject.bean.response.voucher;

import lombok.Data;

@Data
public class VoucherResponse {

    private Long voucherId;

    private String code;

    private Double discount;

    private Double minOrderAmount;
}
