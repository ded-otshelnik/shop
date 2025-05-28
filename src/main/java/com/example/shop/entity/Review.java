package com.example.shop.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column
    @Setter
    @NonNull
    private String reviewText;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @NonNull
    private Product product;
}
