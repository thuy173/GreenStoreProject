package com.example.greenstoreproject.controller;

import com.example.greenstoreproject.entity.Payment;
import com.example.greenstoreproject.repository.PaymentRepository;
import com.example.greenstoreproject.service.PaymentService;
import com.stripe.model.PaymentIntent;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
@SecurityRequirement(name = "bearerAuth")
public class PaymentController {
    private final PaymentService paymentService;
    private final PaymentRepository paymentRepository;

    @PostMapping
    public ResponseEntity<String> handlePayment(@RequestBody Map<String, Object> paymentData) {
        Object orderIdObj = paymentData.get("orderId");
        Object amountObj = paymentData.get("amount");
        Object currencyObj = paymentData.get("currency");
        Object statusObj = paymentData.get("status");
        Object paymentIntentIdObj = paymentData.get("paymentIntentId");

        if (orderIdObj== null || amountObj == null || currencyObj == null || statusObj == null || paymentIntentIdObj == null) {
            return ResponseEntity.badRequest().body("Invalid payment data");
        }

        try {
            Long orderId = ((Number) orderIdObj).longValue();
            Double amount = ((Number) amountObj).doubleValue();
            String currency = currencyObj.toString();
            String status = statusObj.toString();
            String paymentIntentId = paymentIntentIdObj.toString();

            Payment payment = paymentService.savePayment(orderId, amount, currency, status);
            payment.setPaymentIntentId(paymentIntentId);

            paymentRepository.save(payment);

            return ResponseEntity.ok("Payment processed successfully");
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid number format");
        }
    }
}
