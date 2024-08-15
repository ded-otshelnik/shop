package com.example.shop.controller;

import com.example.shop.entity.Cart;
import com.example.shop.entity.Product;
import com.example.shop.exception.ResourceNotFoundException;
import com.example.shop.service.ProductService;
import com.example.shop.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Tag(name = "CartController", description = "Cart controller")
public class CartController {
    private static final Logger logger = LoggerFactory.getLogger(CartController.class.getName());

    private final ProductService productService;
    private final Cart cart;

    @GetMapping("get")
    @Operation(
            summary = "Getting cart",
            description = "Remove product from cart"
    )
    public Cart getCart(){
        logger.info(cart.toString());
        return cart;

    }
    @PostMapping("add")
    @Transactional
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Adding to cart",
            description = "Add product to cart"
    )
    public void addToCart(@RequestParam(name = "product_id") Long productId){
        logger.info("Adding to cart");
        Product product = productService.getProduct(productId).orElseThrow(() -> new ResourceNotFoundException("Incorrect product id"));
        cart.addOrIncrement(product);
    }

    @DeleteMapping("remove")
    @Transactional
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Removing from cart",
            description = "Remove product from cart"
    )
    public void removeFromCart(@RequestParam(name = "product_id") Long productId){
        logger.info("Removing from cart");
        Product product = productService.getProduct(productId).orElseThrow(() -> new ResourceNotFoundException("Incorrect product id"));
        cart.decrementOrRemove(product);
    }
}