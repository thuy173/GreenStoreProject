package com.example.greenstoreproject.controller;

import com.example.greenstoreproject.bean.request.customer.CustomerRegisterRequest;
import com.example.greenstoreproject.entity.Customers;
import com.example.greenstoreproject.service.CartService;
import com.example.greenstoreproject.service.GoogleTokenVerifier;
import com.example.greenstoreproject.service.impl.AuthServiceImpl;
import com.example.greenstoreproject.service.impl.CustomerServiceImpl;
import com.example.greenstoreproject.util.JwtUtil;
import com.example.greenstoreproject.bean.request.customer.CustomerLoginRequest;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {
    private final AuthServiceImpl userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final CustomerServiceImpl customerService;
    private final CartService cartService;
    private final GoogleTokenVerifier googleTokenVerifier;


    @PostMapping("/register")
    public String register(@Valid @RequestBody CustomerRegisterRequest customerRegisterRequest){
        customerService.createUser(customerRegisterRequest);
        return "Register successfully";
    }

    @PostMapping("/login")
    public String loginUser(@Valid @RequestBody CustomerLoginRequest authenticationRequest, HttpServletRequest request) throws Exception{
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
                authenticationRequest.getPassword()));

        final UserDetails userDetails = userService.loadUserByUsername(
                authenticationRequest.getEmail()
        );
        final Long customerId = userService.getCustomerIdByEmail(authenticationRequest.getEmail());

        String cartUuid = getCartUuidFromRequest(request);

        // Merge carts upon login
        cartService.mergeCartUponLogin(customerId, cartUuid);
        final String jwt = jwtUtil.generateToken(userDetails, customerId);
        return jwt;
    }

    @PostMapping(value = "/google-login")
    public String googleLogin(@RequestBody String googleToken) {
        try {
            GoogleIdToken.Payload payload = googleTokenVerifier.verify(googleToken).getPayload();
            String email = payload.getEmail();
            String name = (String) payload.get("name");
            Customers customer = customerService.findByEmail(email);
            if (customer == null) {
                CustomerRegisterRequest registerRequest = new CustomerRegisterRequest();
                registerRequest.setEmail(email);
                registerRequest.setFirstName(name);
                registerRequest.setPassword(UUID.randomUUID().toString());
                customer = customerService.createUser(registerRequest);
            }
            final UserDetails userDetails = userService.loadUserByUsername(email);
            final Long customerId = userService.getCustomerIdByEmail(email);
            final String jwt = jwtUtil.generateToken(userDetails, customerId);
            return jwt;
        } catch (Exception e) {
            return "Google login failed";
        }
    }




    private String getCartUuidFromRequest(HttpServletRequest request) {
        String cartUuid = request.getHeader("X-CART_UUID");
        if (cartUuid != null && !cartUuid.isEmpty()) {
            return cartUuid;
        }

        Cookie cartUuidCookie = WebUtils.getCookie(request, "CART_UUID");
        if (cartUuidCookie != null) {
            return cartUuidCookie.getValue();
        }

        cartUuid = request.getParameter("CART_UUID");
        if (cartUuid != null && !cartUuid.isEmpty()) {
            return cartUuid;
        }

        return null;
    }
}
