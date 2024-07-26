package com.example.greenstoreproject.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "combos")
public class Combo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "combo_id")
    private int comboId;

    @Column(name = "combo_name", nullable = false)
    private String comboName;

    @Enumerated(EnumType.STRING)
    @Column(name = "bmi_status", nullable = false)
    private BMIStatus bmiStatus;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "combo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ComboProduct> comboProducts = new ArrayList<>();

    @Column(name = "price")
    private Double price;
}
