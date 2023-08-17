package com.example.shop.dao;

import com.example.shop.entity.Product;
import com.example.shop.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductDAO {
    private ProductRepository productRepository;
    @Autowired
    public ProductDAO(ProductRepository productRepository){
        this.productRepository = productRepository;
    }


    public Product getProduct(Long productId){
        return productRepository.findById(productId.longValue());
    }
    public List<Product> getProducts(){
        return productRepository.findAll();
    }
    public boolean exists(Long productId){
        return productRepository.existsById(productId);
    }
}
