package com.example.greenstoreproject.mapper;

import com.example.greenstoreproject.bean.request.customer.CustomerRegisterRequest;
import com.example.greenstoreproject.entity.Customers;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {
    public static Customers convertToCustomerEntity(CustomerRegisterRequest customerRegisterRequest) {
        Customers user = new Customers();
        user.setFirstName(customerRegisterRequest.getFirstName());
        user.setLastName(customerRegisterRequest.getLastName());
        user.setEmail(customerRegisterRequest.getEmail());
        user.setPassword(customerRegisterRequest.getPassword());
        user.setPhoneNumber(customerRegisterRequest.getPhoneNumber());
        return user;
    }
}
