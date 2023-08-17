package com.example.shop.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "products")
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    @Getter
    private Long id;

    @Column(name = "product_name",nullable = false)
    @Getter
    @Setter
    @NonNull
    private String productName;

    @Column(name="description")
    @Getter
    @Setter
    private String description;

    @Column(name = "price",nullable = false)
    @Getter
    @Setter
    @NonNull
    private Double price;

    @Column(name = "images")
    @Getter
    @Setter
    private String images;

    @Column(name = "reviews")
    @OneToMany
    @JsonIgnore
    private Set<Review> reviews = new HashSet<>();


    public void AddReview(Review review){
        reviews.add(review);
        review.setProduct(this);
    }


    public void RemoveReview(Review review){
        reviews.remove(review);
        review.setProduct(this);
    }

    public Product(String productName,Double price){
        this.productName=productName;
        this.price=price;
    }
}