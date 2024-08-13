package com.example.shop.controller;

import com.example.shop.entity.Cart;
import com.example.shop.service.OrderService;
import com.example.shop.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final Cart cart;

    @PostMapping("create-order")
    @ResponseStatus(HttpStatus.OK)
    public void createOrder(@RequestParam String login){
        orderService.createOrder(login, cart);
    }

    @DeleteMapping("delete-order")
    @ResponseStatus(HttpStatus.OK)
    public void deleteOrder(@RequestParam String login, @RequestParam("order_id") Long orderId){
        orderService.deleteOrder(login, orderId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getOrders(@RequestParam String login){
        return orderService.getUserOrders(login);
    }
}