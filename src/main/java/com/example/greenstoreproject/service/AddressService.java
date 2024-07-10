package com.example.greenstoreproject.service;

import com.example.greenstoreproject.bean.request.address.AddressRequest;
import com.example.greenstoreproject.bean.request.category.CategoryRequest;
import com.example.greenstoreproject.bean.response.address.AddressDetailResponse;
import com.example.greenstoreproject.bean.response.address.AddressResponse;
import com.example.greenstoreproject.bean.response.category.CategoryDetailResponse;
import com.example.greenstoreproject.bean.response.category.CategoryResponse;
import com.example.greenstoreproject.entity.Address;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AddressService {
    List<AddressResponse> getAllAddress();

    AddressDetailResponse getAddressById(Long id);

    String createAddress(AddressRequest addressRequest);

    String updateAddress(Long id, AddressRequest addressRequest);

    String updateAddressIsActive(Long customerId, Long addressId, Boolean isActive);

    String deleteAddress(Long id);
}
