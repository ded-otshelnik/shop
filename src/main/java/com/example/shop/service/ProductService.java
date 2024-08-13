package com.example.shop.service;

import com.example.shop.entity.Product;
import com.example.shop.repo.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Optional<Product> getProduct(Long productId){
        return productRepository.findById(productId.longValue());
    }
    public List<Product> getProducts(){
        return productRepository.findAll();
    }
    public boolean exists(Long productId){
        return productRepository.existsById(productId);
    }
}
