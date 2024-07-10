package com.example.greenstoreproject.bean.response.address;

import lombok.Data;

@Data
public class AddressDetailResponse {
    private Long addressId;
    private String province;
    private String district;
    private String ward;
    private String addressDetail;
    private Boolean isActive;
}
