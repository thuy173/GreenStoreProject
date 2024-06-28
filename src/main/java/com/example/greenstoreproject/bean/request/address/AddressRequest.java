package com.example.greenstoreproject.bean.request.address;

import lombok.Data;

@Data
public class AddressRequest {
    private String province;
    private String district;
    private String ward;
    private String addressDetail;
    private Boolean isActive;
}
