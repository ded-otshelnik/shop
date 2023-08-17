package com.example.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @Getter
    private Long id;

    @Column(name = "surname", nullable = false)
    @Getter
    @Setter
    private String surname;

    @Column(name = "login", unique = true, nullable = false)
    @Getter
    @Setter
    @NonNull
    private String login;

    @Column(name = "password", nullable = false)
    @Getter
    @Setter
    @NonNull
    @JsonIgnore
    private String password;

    @Column(name = "user_email")
    @Getter
    @Setter
    private String email;

    @Column(name = "email_is_comfirmed")
    @Getter
    @Setter
    private Boolean emailIsComfirmed = false;

    @Column(name = "phone_number")
    @Getter
    @Setter
    private String phoneNumber;

    @Column(name = "role")
    @Getter
    @Setter
    private String role;

    @Column(name = "img")
    @Getter
    @Setter
    private String img;

    public User(String surname, String login, String password, String email, String phoneNumber, String role, String img) {
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.img = img;
    }


    @OneToMany
    @Getter
    private final Set<Order> orders = new HashSet<>();

    public void addOrder(Order order) {
        orders.add(order);
        order.setUser(this);
    }

    public void removeOrder(Order order) {
        orders.remove(order);
        order.setUser(null);
    }

    @OneToMany
    @Getter
    private Set<Review> reviews = new HashSet<>();

    public void addReview(Review review) {
        reviews.add(review);
        review.setUser(this);
    }

    public void removeReview(Review review) {
        reviews.remove(review);
        review.setUser(null);
    }

    @Getter
    @Column(name = "favorites")
    @ElementCollection
    List<Long> favorites = new ArrayList<Long>();

    public void addToFav(Long productId) {
        favorites.add(productId);
    }

    public boolean removeFromFav(Long productId) {
        if (!favorites.isEmpty()) {
            return favorites.remove(productId);
        }
        return false;
    }

    @Getter
    @Column(name = "cart")
    @ElementCollection
    List<Long> cart = new ArrayList<>();

    public boolean addToCart(Long productId) {
        return cart.add(productId);
    }

    public boolean removeFromCart(Long productId) {
        if (!cart.isEmpty())
            return cart.remove(productId);
        return false;
    }

    public boolean clearCart() {
        cart = new ArrayList<>();
        return true;
    }
}