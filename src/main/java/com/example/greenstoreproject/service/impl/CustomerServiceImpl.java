package com.example.greenstoreproject.service.impl;

import com.example.greenstoreproject.bean.request.customer.CustomerRegisterRequest;
import com.example.greenstoreproject.entity.Customers;
import com.example.greenstoreproject.exception.EmailAlreadyExistsException;
import com.example.greenstoreproject.mapper.AuthMapper;
import com.example.greenstoreproject.repository.CustomerRepository;
import com.example.greenstoreproject.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public Customers createUser(CustomerRegisterRequest customerRegisterRequest) {
        if (customerRepository.existsByEmail(customerRegisterRequest.getEmail())) {
            throw new EmailAlreadyExistsException("Email is already taken");
        }
        customerRegisterRequest.setPassword(passwordEncoder.encode(customerRegisterRequest.getPassword()));
        Customers newUser = AuthMapper.convertToCustomerEntity(customerRegisterRequest);

        return customerRepository.save(newUser);
    }
}
