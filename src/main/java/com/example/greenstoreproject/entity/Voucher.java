package com.example.greenstoreproject.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.security.SecureRandom;
import java.util.Base64;

@Entity
@Data
@Table(name = "vouchers")
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voucher_id")
    private Long voucherId;

    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @Column(name = "discount", nullable = false)
    private Double discount;

    @Column(name = "min_order_amount", nullable = false)
    private Double minOrderAmount;

    @PrePersist
    public void generateCode() {
        this.code = generateRandomCode();
    }

    private String generateRandomCode() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[6];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}
