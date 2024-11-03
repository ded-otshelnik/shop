package com.example.shop.repo;

import com.example.shop.entity.Product;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findById(long id);
    List<Product> getDistinctByPriceIsBetween(@NonNull Double low, @NonNull Double high);
    List<Product> getAllByProductName(@NonNull String name);
}
