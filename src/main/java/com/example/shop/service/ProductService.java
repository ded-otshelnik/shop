package com.example.shop.service;

import com.example.shop.entity.Product;
import com.example.shop.exception.ResourceNotFoundException;
import com.example.shop.repo.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product getProduct(Long productId){
        return productRepository.findById(productId.longValue())
                .orElseThrow(() -> new ResourceNotFoundException("Incorrect product id"));
    }

    public List<Product> getProducts(){
        return productRepository.findAll();
    }

    public boolean exists(Long productId){
        return productRepository.existsById(productId);
    }

    @Transactional
    public void updatePrice(Long productId, Double price){
        // don't update price if new value is negative
        if (price <= 0) {
            throw new IllegalArgumentException("Price cannot be less or equal 0");
        }

        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()){
            throw new ResourceNotFoundException(String.format("Product %d not found", productId));
        }
        Product product = optionalProduct.get();

        product.setPrice(price);

        productRepository.save(product);
    }
}
