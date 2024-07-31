package com.example.greenstoreproject.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "advice")
public class Advice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "advice_id")
    private int adviceId;

    @Column(name = "content", length = 65535)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private BMIStatus status;
}
