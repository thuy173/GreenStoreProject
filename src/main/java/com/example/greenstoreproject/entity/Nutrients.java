package com.example.greenstoreproject.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "nutrients")
public class Nutrients {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nutrient_id")
    private Long nutrientId;

    @Column(name = "nutrient_name")
    private String nutrientName;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "nutrients")
    private List<Products> products;
}
