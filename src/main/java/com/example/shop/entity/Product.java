package com.example.shop.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Data
@Entity
@Table(name = "products")
@NoArgsConstructor
@RequiredArgsConstructor
public class Product{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "product_name",nullable = false)
    @Setter
    @NonNull
    private String productName;

    @Column(name = "price",nullable = false)
    @Setter
    @NonNull
    private Double price;
}