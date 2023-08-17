package com.example.shop.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "orders")
@NoArgsConstructor
public class Order{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    @Getter
    private Long id;

    @Column(name = "products")
    @OneToMany
    private Set<OrderItem> products = new HashSet<>();

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private User user;

    @Column(name = "payment_was_processed")
    @Getter
    @Setter
    private boolean paymentWasProcessed = false;

    @JsonGetter("user_id")
    public Long GetUserId(){
        return user.getId();
    }
    @Column(name = "total")
    private Double price=0.0;

    public void addProduct(OrderItem item){
        products.add(item);
        item.setOrder(this);
        price+=item.getProduct().getPrice()*item.getCount();
    }

    public void removeProduct(OrderItem item){
        products.remove(item);
        item.setOrder(null);
        price-=item.getProduct().getPrice()*item.getCount();
    }
}