package com.example.greenstoreproject.service;

import com.example.greenstoreproject.bean.request.category.CategoryRequest;
import com.example.greenstoreproject.bean.request.customer.CustomerRegisterRequest;
import com.example.greenstoreproject.bean.request.customer.CustomerUpdateAvatarRequest;
import com.example.greenstoreproject.bean.request.customer.CustomerUpdateRequest;
import com.example.greenstoreproject.bean.response.category.CategoryDetailResponse;
import com.example.greenstoreproject.bean.response.category.CategoryResponse;
import com.example.greenstoreproject.bean.response.customer.CustomerResponse;
import com.example.greenstoreproject.entity.Customers;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface CustomerService {
    Customers createUser(CustomerRegisterRequest customerRegisterRequest);

    List<CustomerResponse> getAllCustomer();

    CustomerResponse getCustomerById(Long id);

    Map<String, String> updateCustomer(Long id, CustomerUpdateRequest customerUpdateRequest);

    String updateAvatar(Long id, CustomerUpdateAvatarRequest customerUpdateRequest);

    String deleteCustomer(Long id);

    Customers findByEmail(String email);
}
