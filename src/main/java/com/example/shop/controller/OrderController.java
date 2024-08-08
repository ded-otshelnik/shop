package com.example.shop.controller;

import com.example.shop.dao.OrderDAO;
import com.example.shop.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/user/orders")
public class OrderController {

    private final OrderDAO orderDAO;

    @Autowired
    public OrderController(OrderDAO orderDAO){
        this.orderDAO = orderDAO;
    }

    @PostMapping("create-order")
    public ResponseEntity<String> createOrder(@RequestParam String login){
        return orderDAO.createOrder(login);
    }

    @DeleteMapping("delete-order")
    public ResponseEntity<String> deleteOrder(@RequestParam String login, @RequestParam("order_id") long orderId){
        return orderDAO.deleteOrder(login, orderId);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getOrders(@RequestParam String login){
        return orderDAO.getUserOrders(login);
    }
}