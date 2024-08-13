package com.example.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Data
@Entity
@Table(name = "products")
@NoArgsConstructor
@RequiredArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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