package com.example.greenstoreproject.service;

import com.example.greenstoreproject.entity.Orders;
import com.example.greenstoreproject.entity.Payment;
import com.example.greenstoreproject.repository.OrderRepository;
import com.example.greenstoreproject.repository.PaymentRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeApiKey;
    }

    public PaymentIntent createPaymentIntent( Double amount, String currency, String description, String customerEmail) throws StripeException {
        Map<String, Object> params = new HashMap<>();
        params.put("amount", amount);
        params.put("currency", currency);
        params.put("description", description);
        params.put("receipt_email", customerEmail);

        return PaymentIntent.create(params);
    }

    public Payment savePayment(Long orderId, Double amount, String currency, String status) {
        Orders order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Invalid order ID"));

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(amount.doubleValue());
        payment.setCurrency(currency);
        payment.setStatus(status);

        return paymentRepository.save(payment);
    }
}

