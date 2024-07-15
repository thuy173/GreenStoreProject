package com.example.greenstoreproject.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "order_code", unique = true, nullable = false)
    private String orderCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customers customer;

    @Column(name = "guest_name")
    private String guestName;

    @Column(name = "guest_email")
    private String guestEmail;

    @Column(name = "guest_phone")
    private String guestPhone;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voucher_id")
    private Voucher voucher;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "shipping_address")
    private String shippingAddress;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItems> orderItems = new ArrayList<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratings = new ArrayList<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @PrePersist
    public void generateOrderCode() {
        this.orderCode = generateRandomCode();
    }

    private String generateRandomCode() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[6];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    public boolean canApplyVoucher() {
        if (voucher != null && totalAmount != null) {
            return totalAmount >= voucher.getMinOrderAmount();
        }
        return false;
    }

    public void setDefaultShippingAddress() {
        if (customer != null) {
            List<Address> activeAddresses = customer.getAddress().stream()
                    .filter(address -> Boolean.TRUE.equals(address.getIsActive()))
                    .collect(Collectors.toList());

            if (!activeAddresses.isEmpty()) {
                Address defaultAddress = activeAddresses.get(0);
                this.shippingAddress = defaultAddress.getAddressDetail() + ", " +
                        defaultAddress.getWard() + ", " + defaultAddress.getDistrict()
                        + ", " + defaultAddress.getProvince();
            }
        }
    }
}
