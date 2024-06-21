package com.example.greenstoreproject.controller;

import com.example.greenstoreproject.bean.request.customer.CustomerRegisterRequest;
import com.example.greenstoreproject.entity.Customers;
import com.example.greenstoreproject.mapper.AuthMapper;
import com.example.greenstoreproject.repository.CustomerRepository;
import com.example.greenstoreproject.service.impl.AuthServiceImpl;
import com.example.greenstoreproject.service.impl.CustomerServiceImpl;
import com.example.greenstoreproject.util.JwtUtil;
import com.example.greenstoreproject.bean.request.customer.CustomerLoginRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {
    private final AuthServiceImpl userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final CustomerServiceImpl customerService;

    @PostMapping("/register")
    public String register(@Valid @RequestBody CustomerRegisterRequest customerRegisterRequest){
        customerService.createUser(customerRegisterRequest);
        return "Register successfully";
    }

    @PostMapping("/login")
    public String loginUser(@Valid @RequestBody CustomerLoginRequest authenticationRequest) throws Exception{
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
                authenticationRequest.getPassword()));

        final UserDetails userDetails = userService.loadUserByUsername(
                authenticationRequest.getEmail()
        );

        final String jwt = jwtUtil.generateToken(userDetails);
        return jwt;
    }
}
