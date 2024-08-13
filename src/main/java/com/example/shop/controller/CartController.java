package com.example.shop.controller;

import com.example.shop.entity.Cart;
import com.example.shop.entity.Product;
import com.example.shop.exception.ResourceNotFoundException;
import com.example.shop.service.ProductService;
import com.example.shop.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private static final Logger logger = LoggerFactory.getLogger(CartController.class.getName());

    private final UserService userService;
    private final ProductService productService;
    private final Cart cart;

    @GetMapping("get")
    public Cart getCart(){
        logger.info(cart.toString());
        return cart;

    }
    @PostMapping("add")
    @Transactional
    @ResponseStatus(HttpStatus.OK)
    public void addToCart(@RequestParam(name = "product_id") Long productId){
        logger.info("Adding to cart");

        Product product = productService.getProduct(productId).orElseThrow(() -> new ResourceNotFoundException("Incorrect product id"));
        cart.addOrIncrement(product);
    }

    @DeleteMapping("remove")
    @Transactional
    @ResponseStatus(HttpStatus.OK)
    public void removeFromCart(@RequestParam(name = "product_id") Long productId){
        logger.info("Removing from cart");

        Product product = productService.getProduct(productId).orElseThrow(() -> new ResourceNotFoundException("Incorrect product id"));
        cart.decrementOrRemove(product);
    }
}