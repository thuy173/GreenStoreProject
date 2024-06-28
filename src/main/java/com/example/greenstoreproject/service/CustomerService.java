package com.example.greenstoreproject.service;

import com.example.greenstoreproject.bean.request.category.CategoryRequest;
import com.example.greenstoreproject.bean.request.customer.CustomerRegisterRequest;
import com.example.greenstoreproject.bean.request.customer.CustomerUpdateRequest;
import com.example.greenstoreproject.bean.response.category.CategoryDetailResponse;
import com.example.greenstoreproject.bean.response.category.CategoryResponse;
import com.example.greenstoreproject.bean.response.customer.CustomerResponse;
import com.example.greenstoreproject.entity.Customers;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {
    Customers createUser(CustomerRegisterRequest customerRegisterRequest);

    List<CustomerResponse> getAllCustomer();

    CustomerResponse getCustomerById(Long id);

    String updateCustomer(Long id, CustomerUpdateRequest customerUpdateRequest);

    String deleteCustomer(Long id);
}
