package com.example.greenstoreproject.bean.request.voucher;

import lombok.Data;

@Data
public class VoucherRequest {

    private Double discount;

    private Double minOrderAmount;
}
