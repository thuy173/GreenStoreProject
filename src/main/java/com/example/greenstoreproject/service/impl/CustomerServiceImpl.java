package com.example.greenstoreproject.service.impl;

import com.cloudinary.Cloudinary;
import com.example.greenstoreproject.bean.request.customer.CustomerRegisterRequest;
import com.example.greenstoreproject.bean.request.customer.CustomerUpdateAvatarRequest;
import com.example.greenstoreproject.bean.request.customer.CustomerUpdateRequest;
import com.example.greenstoreproject.bean.response.customer.CustomerResponse;
import com.example.greenstoreproject.entity.Customers;
import com.example.greenstoreproject.exception.EmailAlreadyExistsException;
import com.example.greenstoreproject.exception.EmptyException;
import com.example.greenstoreproject.exception.NotFoundException;
import com.example.greenstoreproject.mapper.AuthMapper;
import com.example.greenstoreproject.mapper.CustomerMapper;
import com.example.greenstoreproject.repository.CustomerRepository;
import com.example.greenstoreproject.service.CustomerService;
import com.example.greenstoreproject.util.JwtUtil;
import com.example.greenstoreproject.util.SuccessMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final Cloudinary cloudinary;
    private final JwtUtil jwtUtil;

    private static final String DEFAULT_AVATAR_URL = "http://res.cloudinary.com/dmmk9racr/image/upload/v1720583529/z4hzrmjbq1cc3dvz1xjz.png";

    @Override
    public Customers createUser(CustomerRegisterRequest customerRegisterRequest) {
        if (customerRepository.existsByEmail(customerRegisterRequest.getEmail())) {
            throw new EmailAlreadyExistsException("Email is already taken");
        }
        customerRegisterRequest.setPassword(passwordEncoder.encode(customerRegisterRequest.getPassword()));
        Customers newUser = AuthMapper.convertToCustomerEntity(customerRegisterRequest);

        newUser.setAvatar(DEFAULT_AVATAR_URL);

        return customerRepository.save(newUser);
    }

    @Override
    public Customers findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    public List<CustomerResponse> getAllCustomer() {
        List<Customers> customers = customerRepository.findAll();
        if(customers.isEmpty()){
            throw new EmptyException("Customer list is empty");
        }
        return customers.stream()
                .map(CustomerMapper::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerResponse getCustomerById(Long id) {
        Customers customers = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found " + id));
        return CustomerMapper.convertToDetailResponse(customers);
    }

    @Override
    public Map<String, String> updateCustomer(Long id, CustomerUpdateRequest customerUpdateRequest) {
        Customers customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found " + id));

        boolean emailUpdated = !customer.getEmail().equals(customerUpdateRequest.getEmail());
        CustomerMapper.updateFromRequest(customer, customerUpdateRequest);
        customerRepository.save(customer);

        Map<String, String> response = new HashMap<>();
        response.put("message", SuccessMessage.SUCCESS_UPDATED.getMessage());

        if (emailUpdated) {
            UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                    customer.getEmail(), customer.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER"))
            );
            String newToken = jwtUtil.generateToken(userDetails, customer.getCustomerId());
            response.put("token", newToken);
        }

        return response;
    }

    @Override
    public String updateAvatar(Long id, CustomerUpdateAvatarRequest customerUpdateRequest) {
        Customers customers = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found " + id));

        CustomerMapper.updateAvatar(cloudinary, customers, customerUpdateRequest);
        customerRepository.save(customers);
        return SuccessMessage.SUCCESS_UPDATED.getMessage();
    }

    @Override
    public String deleteCustomer(Long id) {
        Customers customers = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found " + id));
        customerRepository.delete(customers);
        return SuccessMessage.SUCCESS_DELETED.getMessage();
    }
}
