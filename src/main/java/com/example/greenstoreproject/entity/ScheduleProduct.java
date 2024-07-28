package com.example.greenstoreproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "scheduled_products")
public class ScheduleProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scheduled_product_id")
    private Long scheduledProductId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    @JsonIgnore
    private Schedule schedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "combo_product_id")
    @JsonIgnore
    private ComboProduct comboProduct;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "day_in_regimen")
    private Integer dayInRegimen;
}
