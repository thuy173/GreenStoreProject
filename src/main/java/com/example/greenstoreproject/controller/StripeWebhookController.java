package com.example.greenstoreproject.controller;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stripe")
public class StripeWebhookController {

    @PostMapping("/webhook")
    public ResponseEntity<String> handleStripeWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        String endpointSecret = "whsec_5741e461c2b0c4fe09b6366ff6c1c9383a51202cdb0967dca0ec2ebc4db3d978";
        Event event = null;

        try {
            event = Webhook.constructEvent(
                    payload, sigHeader, endpointSecret
            );
        } catch (SignatureVerificationException e) {
            // Invalid signature
            return ResponseEntity.badRequest().build();
        }

        // Handle the event
        switch (event.getType()) {
            case "payment_intent.succeeded":
                PaymentIntent paymentIntent = (PaymentIntent) event.getData().getObject();
                System.out.println("PaymentIntent was successful for order ID: {}" + paymentIntent.getDescription());
                // Update order status in your system
                break;
            // ... handle other event types
            default:
                System.out.println("Unhandled event type: {}" + event.getType());
        }

        return ResponseEntity.ok("Success");
    }
}
