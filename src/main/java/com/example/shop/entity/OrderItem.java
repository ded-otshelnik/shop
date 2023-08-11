package com.example.shop.entity;

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
    @ManyToOne(cascade = CascadeType.MERGE)
    private Order order;

    @Getter
    @OneToOne(fetch = FetchType.LAZY)
    @NonNull
    private Product product;

    @Getter
    @NonNull
    @Column
    private Long count;
}