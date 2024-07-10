package com.example.greenstoreproject.bean.response.customer;

import com.example.greenstoreproject.bean.response.address.AddressResponse;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CustomerResponse {
    private Long customerId;

    private String fullName;

    private String email;

    private String phoneNumber;

    private String avatar;

    private List<AddressResponse> address = new ArrayList<>();
}
