package com.example.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Getter
@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@RequiredArgsConstructor
public class User implements UserDetails {
    @Id
    @Column(unique = true, nullable = false)
    @Setter
    @NonNull
    private String login;

    @JsonIgnore
    @NonNull
    private String password;

    @Transient
    private String passwordConfirm;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    @Setter
    @NonNull
    private Role role;

    @Setter
    private String surname;

    @Column(name = "user_email")
    @Setter
    private String email;

    @Transient
    @Setter
    private Boolean emailIsConfirmed = false;

    @Setter
    private String phoneNumber;

    @OneToMany
    private Set<Review> reviews = new HashSet<>();

    public void addReview(Review review) {
        reviews.add(review);
        review.setUser(this);
    }

    public void removeReview(Review review) {
        reviews.remove(review);
        review.setUser(null);
    }

    @Column(name = "favorites")
    @ElementCollection
    List<Long> favorites = new ArrayList<Long>();

    public boolean addToFav(Long productId) {
        return favorites.add(productId);
    }

    public boolean removeFromFav(Long productId) {
        return favorites.remove(productId);
    }

    @Column(name = "cart")
    @ElementCollection
    List<Long> cart = new ArrayList<>();

    public boolean addToCart(Long productId) {
        return cart.add(productId);
    }

    public boolean removeFromCart(Long productId) {
        return cart.remove(productId);
    }

    public boolean clearCart() {
        cart = new ArrayList<>();
        return true;
    }

    // Spring Security part

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public @NonNull String getPassword() {
        return password;
    }
}