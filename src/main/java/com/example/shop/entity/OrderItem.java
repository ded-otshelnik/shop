package com.example.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.PostConstruct;
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
    @Setter
    @ManyToOne
    private Order order;

    @Getter
    @Column
    private Long quantity;

    @OneToOne
    @NonNull
    @Getter
    private Product product;

    @Getter
    private Double price;

    @PostConstruct
    public void init(){
        quantity = 1L;
        price = product.getPrice();
    }

    public void increment(){
        quantity++;
        price = product.getPrice() * quantity;
    }

    public void decrement(){
        quantity++;
        price = product.getPrice() * quantity;
    }
}