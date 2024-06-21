package com.example.greenstoreproject.service;

import com.example.greenstoreproject.bean.request.customer.CustomerRegisterRequest;
import com.example.greenstoreproject.entity.Customers;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {
    Customers createUser(CustomerRegisterRequest customerRegisterRequest);
}
