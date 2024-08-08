package com.example.shop.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
public class OrderItem{
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @JsonIgnore
    @ManyToOne
    private Order order;

    @JsonIgnore
    @OneToOne
    @NonNull
    private Product product;

    @JsonGetter("product_id")
    public long getProduct(){
        return product.getId();
    }

    public double getProductPrice(){
        return product.getPrice() * count;
    }

    @Getter
    @NonNull
    @Column
    private Long count;
}