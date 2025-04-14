package com.example.shop.service;

import com.example.shop.entity.Cart;
import com.example.shop.entity.OrderItem;
import com.example.shop.entity.Product;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductService productService;
    private final Cart cart;

    public List<OrderItem> getCart(){
        return cart.getItems();
    }

    @Transactional
    public void addToCart(Long productId){
        Product product = productService.getProduct(productId);
        cart.addOrIncrement(product);
    }

    @Transactional
    public void removeFromCart(Long productId){
        Product product = productService.getProduct(productId);
        cart.decrementOrRemove(product);
    }
}
