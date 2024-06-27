package com.example.greenstoreproject.service;

import com.example.greenstoreproject.bean.request.address.AddressRequest;
import com.example.greenstoreproject.bean.request.category.CategoryRequest;
import com.example.greenstoreproject.bean.response.address.AddressResponse;
import com.example.greenstoreproject.bean.response.category.CategoryDetailResponse;
import com.example.greenstoreproject.bean.response.category.CategoryResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AddressService {
    List<AddressResponse> getAllAddress();

    AddressResponse getAddressById(Long id);

    String createAddress(AddressRequest addressRequest);

    String updateAddress(Long id, AddressRequest addressRequest);

    String deleteAddress(Long id);
}
