package com.example.shop.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private Product product;

    @JsonGetter("productId")
    public Long getProduct() {
        return product.getId();
    }
}
