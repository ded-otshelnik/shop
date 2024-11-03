package com.example.shop.controller;

import com.example.shop.entity.Cart;
import com.example.shop.entity.OrderItem;
import com.example.shop.entity.Product;
import com.example.shop.exception.ResourceNotFoundException;
import com.example.shop.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "CartController", description = "Cart controller")
public class CartController {

    private final ProductService productService;
    private final Cart cart;

    @GetMapping("get")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Getting cart",
            description = "Remove product from cart"
    )
    public List<OrderItem> getCart(){
        return cart.getItems();

    }
    @PostMapping("add")
    @Transactional
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Adding to cart",
            description = "Add product to cart"
    )
    public void addToCart(@RequestParam(name = "product_id") Long productId){
        log.info("Adding to cart");
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
        log.info("Removing from cart");
        Product product = productService.getProduct(productId).orElseThrow(() -> new ResourceNotFoundException("Incorrect product id"));
        cart.decrementOrRemove(product);
    }
}