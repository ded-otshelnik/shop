package com.example.shop.entity;


import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name = "review")
public class Review {
    @Id
    @Column(name = "review_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Column(name="text")
    @Getter
    @Setter
    private String text;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private User user;

    @JsonGetter("user_id")
    public Long GetUserId(){
        return user.getId();
    }
    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private Product product;

    @JsonGetter("product_id")
    public Long GetProductId(){
        return user.getId();
    }

    @Column(name = "images")
    private String images;
}
