package com.example.shop.controller;

import com.example.shop.entity.OrderItem;
import com.example.shop.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    private final CartService cartService;

    @GetMapping("get")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Getting cart",
            description = "Remove product from cart"
    )
    public List<OrderItem> getCart(){
        log.info("Get cart");
        return cartService.getCart();

    }
    @PostMapping("add")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Adding to cart",
            description = "Add product to cart"
    )
    public void addToCart(@RequestParam(name = "product_id") Long productId){
        log.info("Adding to cart");
        cartService.addToCart(productId);
    }

    @DeleteMapping("remove")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Removing from cart",
            description = "Remove product from cart"
    )
    public void removeFromCart(@RequestParam(name = "product_id") Long productId){
        log.info("Removing from cart");
        cartService.removeFromCart(productId);
    }
}