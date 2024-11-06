package com.example.shop.repo;

import com.example.shop.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    @EntityGraph(attributePaths = {"roles"})
    Optional<User> findByUsername(String username);
}