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
import java.util.Set;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderDAO orderDAO;

    @PostMapping("create-order")
    public ResponseEntity<String> createOrder(@RequestParam String login){
        return orderDAO.createOrder(login);
    }
    @DeleteMapping("delete-order")
    public ResponseEntity<String> deleteOrder(@RequestParam("order_id") String orderId){
        return orderDAO.deleteOrder(Long.parseLong(orderId));
    }
    @GetMapping
    public ResponseEntity<Set<Order>> getOrders(@RequestParam String login){
        return new ResponseEntity<Set<Order>>(orderDAO.getUserOrders(login),HttpStatus.OK);
    }
}