package com.example.shop.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Data
@Entity
@NoArgsConstructor
public class OrderItem{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private Long quantity;

    @OneToOne
    @NonNull
    private Product product;

    private Double price;

    public OrderItem(Product product){
        this.product = product;
        this.quantity = 1L;
        this.price = product.getPrice();
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