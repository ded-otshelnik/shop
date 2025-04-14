package com.example.shop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @OneToMany(cascade = CascadeType.ALL)
    private List<Review> reviews;

    public void addReview(Review review){
        reviews.add(review);
    }

    public void removeReview(Review review){
        reviews.remove(review);
    }
}